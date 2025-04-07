package com.menglang.bong_rumluos.Bong_rumluos.security.service;

import com.menglang.bong_rumluos.Bong_rumluos.dto.ResponseTemplate;
import com.menglang.bong_rumluos.Bong_rumluos.dto.authentication.*;
import com.menglang.bong_rumluos.Bong_rumluos.dto.me.MeResponse;
import com.menglang.bong_rumluos.Bong_rumluos.entities.Permission;
import com.menglang.bong_rumluos.Bong_rumluos.entities.Role;
import com.menglang.bong_rumluos.Bong_rumluos.entities.User;
import com.menglang.bong_rumluos.Bong_rumluos.exceptionHandler.exceptions.BadRequestException;
import com.menglang.bong_rumluos.Bong_rumluos.exceptionHandler.exceptions.NotFoundException;
import com.menglang.bong_rumluos.Bong_rumluos.exceptionHandler.exceptions.UnauthorizedException;
import com.menglang.bong_rumluos.Bong_rumluos.repositories.RoleRepository;
import com.menglang.bong_rumluos.Bong_rumluos.repositories.UserRepository;
import com.menglang.bong_rumluos.Bong_rumluos.security.jwt.JwtTokenService;
import com.menglang.bong_rumluos.Bong_rumluos.security.userPrincipal.AuthoritiesExtraction;
import com.menglang.bong_rumluos.Bong_rumluos.security.userPrincipal.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private static final Logger log = LoggerFactory.getLogger(AuthenticationServiceImpl.class);
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationMapper authenticationMapper;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final JwtTokenService jwtTokenService;
    private final AuthoritiesExtraction authoritiesExtraction;
    private final AuthenticationManager authenticationManager;


    @Override
    public ResponseTemplate register(RegisterDTO dto) {
        validateUser(dto);
        User user = authenticationMapper.toAuthentication(dto, roleRepository);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        try {
            User savedUser = userRepository.save(user);
            AuthenticationRes authenticationRes = extractAuthenticationResponse(savedUser);
            return ResponseTemplate.builder()
                    .dateTime(LocalDateTime.now())
                    .code((short) 201)
                    .message("user created successful")
                    .data(authenticationRes)
                    .build();

        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public ResponseTemplate authenticate(AuthenticationReq loginDto) {

        User user = userRepository.findByUsername(loginDto.username()).orElseThrow(() -> new NotFoundException("User Not Found"));
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.username(),
                        loginDto.password()
                )
        );

        if (authentication.isAuthenticated()) {
            AuthenticationRes authenticationRes = extractAuthenticationResponse(user);
            return ResponseTemplate.builder()
                   .dateTime(LocalDateTime.now())
                    .code((short) 200)
                    .message("successful")
                    .data(authenticationRes)
                    .build();
        } else {
            throw new UnauthorizedException("Invalid Credentials");
        }
    }

    @Override
    public ResponseTemplate resetPassword(ResetPasswordDTO resetPasswordDTO) {
        User existUser = userRepository.findByUsername(resetPasswordDTO.username()).orElseThrow(() -> new NotFoundException("User Not Found"));

        if (!passwordEncoder.matches(resetPasswordDTO.password(), existUser.getPassword())) {
            throw new BadRequestException("Invalid Credential.");
        }
        existUser.setPassword(passwordEncoder.encode(resetPasswordDTO.password()));
        try {
            User updateUser = userRepository.save(existUser);
            return ResponseTemplate.builder()
                    .data(authenticationMapper.toResponse(updateUser))
                    .message("Updated Successful")
                    .code((short) 200)
                    .build();
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }

    }

    @Override
    public ResponseTemplate getMe(Authentication authentication) {
        String username=authentication.getName();
        User user= userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("User Not Found"));

        List<String > roles=user.getRoles().stream().map(Role::getName).toList();
        List<String> permissions=user.getRoles().stream().flatMap(r->r.getPermissions().stream()).map(Permission::getName).toList();
        MeResponse me=MeResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(username)
                .firstname(user.getFirstName())
                .lastname(user.getLastName())
                .roles(roles)
                .permissions(permissions)
                .build();
      return ResponseTemplate.builder()
              .message("success")
              .data(me)
              .code((short) 200)
              .dateTime(LocalDateTime.now())
              .build();
    }

    private AuthenticationRes extractAuthenticationResponse(User user) {
        UserPrincipal userPrincipal = UserPrincipal.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(authoritiesExtraction.extractRoleGrantAuthorities(user.getRoles()))
                .build();

        String accessToken = jwtTokenService.generateToken(userPrincipal);
        String refreshToken = jwtTokenService.refreshToken(userPrincipal);

        return AuthenticationRes.builder()
                .email(user.getEmail())
                .lastname(user.getLastName())
                .username(user.getUsername())
                .firstname(user.getFirstName())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .roles(user.getRoles().stream().map(Role::getName).toList())
                .build();
    }

    private void validateUser(RegisterDTO dto) {
        try {
            Optional<User> existUser = userRepository.findByUsername(dto.username());
            if (existUser.isPresent()) {
                throw new BadRequestException("User Already Exist.");
            }
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }
}

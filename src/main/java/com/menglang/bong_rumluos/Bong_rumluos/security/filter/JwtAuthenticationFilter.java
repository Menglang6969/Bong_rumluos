package com.menglang.bong_rumluos.Bong_rumluos.security.filter;

import com.menglang.bong_rumluos.Bong_rumluos.constant.JwtProperties;
import com.menglang.bong_rumluos.Bong_rumluos.helper.ObjectMapperConvertor;
import com.menglang.bong_rumluos.Bong_rumluos.security.jwt.JwtTokenService;
import com.menglang.bong_rumluos.Bong_rumluos.security.userDetails.UserDetailsServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtProperties jwtProperties;
    private final JwtTokenService jwtTokenService;
    private final UserDetailsServiceImpl userDetailsService;
    private final ObjectMapperConvertor objectMapperConverter;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        log.info("trigger doFilter...........");
        try {
            String accessToken = request.getHeader(jwtProperties.getHeader());
            if (accessToken == null || !accessToken.startsWith(jwtProperties.getPrefix())) {
                filterChain.doFilter(request, response);
                return;
            }
            log.info("doFilter...........");
            if (!accessToken.isBlank() || !accessToken.isEmpty()) {
                accessToken = accessToken.substring(7);
                if (jwtTokenService.isValidToken(accessToken)) {
                    Claims claims = jwtTokenService.extractClaims(accessToken);
                    String username = claims.getSubject();
                    if (username != null) {
                        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                        //authentication with type UsernamePasswordAuthenticationToken (2factor,...)
                        UsernamePasswordAuthenticationToken authenticationToken = UsernamePasswordAuthenticationToken.authenticated(
                                userDetails.getUsername(), null, userDetails.getAuthorities());

                        //setContext to Spring Security
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                        filterChain.doFilter(request, response);
                    } else {
                        log.warn("Invalid User");
                        sendError(response, "Invalid User");
                        return;
                    };

                } else{
                    log.warn("invalid token");
                    sendError(response, "Invalid token");
                    return;
                };

            } else log.warn("Access token ");
        } catch (ExpiredJwtException ex) {
            log.info("Token expire.");
            sendError(response, "Token expired");
        } catch (Exception ex) {
            log.info("error is user {}", ex.getMessage());
            objectMapperConverter.sendErrorResponse(response, ex.getMessage());
        }
    }

    private void sendError(HttpServletResponse response, String message) throws IOException {
        log.warn("JWT Filter Error Response: {}", message);
        objectMapperConverter.sendErrorResponse(response, message);

    }


}

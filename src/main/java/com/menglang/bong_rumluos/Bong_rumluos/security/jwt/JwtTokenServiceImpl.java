package com.menglang.bong_rumluos.Bong_rumluos.security.jwt;

import com.menglang.bong_rumluos.Bong_rumluos.constant.JwtProperties;
import com.menglang.bong_rumluos.Bong_rumluos.exceptionHandler.exceptions.BadRequestException;
import com.menglang.bong_rumluos.Bong_rumluos.security.userDetails.UserDetailsServiceImpl;
import com.menglang.bong_rumluos.Bong_rumluos.security.userPrincipal.UserPrincipal;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

@RequiredArgsConstructor
@Service
public class JwtTokenServiceImpl implements JwtTokenService {
    private static final Logger log = LoggerFactory.getLogger(JwtTokenServiceImpl.class);
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtProperties jwtProperties;


    @Override
    public Claims extractClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    @Override
    public Key getKey() {
        byte[] keys = Decoders.BASE64.decode(this.jwtProperties.getSecrete());
        return Keys.hmacShaKeyFor(keys);
    }

    @Override
    public String generateToken(UserPrincipal userPrincipal) {
        Instant currentTime = Instant.now();
        log.info("jwt expire: {}", jwtProperties.getRefresh_token_expire());
        return Jwts.builder()
                .subject(userPrincipal.getUsername())
                .claim("authorities", userPrincipal.getAuthorities().stream().map(Object::toString).toList())
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(currentTime.plusSeconds(Long.parseLong(this.jwtProperties.getExpire()))))
                .signWith(getSignInKey())
                .compact();
    }

    @Override
    public String refreshToken(UserPrincipal userPrincipal) {
        Instant currentTime = Instant.now();
        return Jwts.builder()
                .subject(userPrincipal.getUsername())
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(currentTime.plusSeconds(Long.parseLong(this.jwtProperties.getRefresh_token_expire()))))
                .signWith(getSignInKey())
                .compact();
    }

    @Override
    public SecretKey getSignInKey() {
        byte[] bytes = Base64.getDecoder().decode(this.jwtProperties.getSecrete().getBytes(StandardCharsets.UTF_8));
        return new SecretKeySpec(bytes, "HmacSHA256");
    }

    @Override
    public boolean isValidToken(String token) {
        String username = extractUsername(token);
        UserDetails user = userDetailsService.loadUserByUsername(username);
        return user != null;
    }

    private String extractUsername(String token) {
        return extractClaimFunction(token, Claims::getSubject);
    }

    private <T> T extractClaimFunction(String token, Function<Claims, T> claimsFunction) {
        Claims claims = extractAllClaim(token);
        return claimsFunction.apply(claims);
    }

    //decode token to claims data
    private Claims extractAllClaim(String token) {
        try {
            return extractClaims(token);
        } catch (ExpiredJwtException ex) {
            throw new BadRequestException("Token was expired.");
        } catch (UnsupportedJwtException ex) {
            throw new BadRequestException("Token is not support");
        } catch (MalformedJwtException ex) {
            throw new BadRequestException("Token is Invalid");
        } catch (Exception ex) {
            throw new BadRequestException(ex.getMessage());
        }
    }
}

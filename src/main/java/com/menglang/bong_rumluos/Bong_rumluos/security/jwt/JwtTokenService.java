package com.menglang.bong_rumluos.Bong_rumluos.security.jwt;

import com.menglang.bong_rumluos.Bong_rumluos.security.userPrincipal.UserPrincipal;
import io.jsonwebtoken.Claims;

import javax.crypto.SecretKey;
import java.security.Key;

public interface JwtTokenService {
    Claims extractClaims (String token);
    Key getKey();
    String generateToken(UserPrincipal userPrincipal);
    String refreshToken(UserPrincipal userPrincipal);
    SecretKey getSignInKey();
    boolean isValidToken(String token);
}

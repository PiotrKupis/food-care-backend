package com.andromeda.foodcare.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Class responsible for handling operations connected with JWT.
 */
@Service
@Getter
@RequiredArgsConstructor
public class JwtProvider {

    private final RSAPrivateKey privateKey;
    private final RSAPublicKey publicKey;

    public String generateToken(String email) {
        return Jwts.builder()
            .setSubject(email)
            .signWith(privateKey)
            .compact();
    }

    public String getEmailFromJwt(String token) {
        Claims claims = Jwts.parserBuilder()
            .setSigningKey(publicKey)
            .build()
            .parseClaimsJws(token)
            .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(publicKey)
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
}

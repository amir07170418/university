package org.example.university.security;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Long expiration;

    private SecretKey getSecretKey() {
        return  Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
    public String generateToken(String email) {
        return Jwts.builder().subject(email).signWith(getSecretKey()).
                issuedAt(new Date()).expiration(new Date(System.currentTimeMillis() + expiration)).compact();
    }
    public String extractEmail(String token) {
        return Jwts.parser().verifyWith(getSecretKey()).build().parseSignedClaims(token).getPayload().getSubject();
    }
    public Date extractExpiration(String token) {
        return Jwts.parser().verifyWith(getSecretKey()).build().parseSignedClaims(token).getPayload().getExpiration();
    }
    public Boolean validateToken(String token, String email) {
        return  extractEmail(token).equals(email) && extractExpiration(token).after(new Date());
    }
}

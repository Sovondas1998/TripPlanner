package com.example.tripplanner.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component // Registers this class as a Spring-managed Bean
public class JwtUtil {

    private final SecretKey key;   // Secret key used to sign & verify JWT
    private final long expiration; // Token validity duration (ms)

    /**
     * Constructor-based injection of JWT properties
     * Values are read from application.yml
     */
    public JwtUtil(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration}") long expiration
    ) {
        // Convert secret string into HMAC SHA key (required by JJWT)
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expiration = expiration;
    }

    /**
     * Generate JWT token with email + role
     */
    public String generateToken(String email, String role) {
        return Jwts.builder()
                .subject(email)                // "sub" → identifies the user
                .claim("role", role)           // Custom claim (USER / ADMIN)
                .issuedAt(new Date())          // Token creation time
                .expiration(
                        new Date(System.currentTimeMillis() + expiration)
                )                              // Token expiry time
                .signWith(key)                 // Sign using secret key
                .compact();                    // Build final JWT
    }

    /**
     * Convenience method (default role = USER)
     */
    public String generateToken(String email) {
        return generateToken(email, "USER");
    }

    /**
     * Extract email (subject) from JWT
     */
    public String extractEmail(String token) {
        return getClaims(token).getSubject();
    }

    /**
     * Extract role from JWT
     * Used later for Spring Security authorities
     */
    public String extractRole(String token) {
        return getClaims(token).get("role", String.class);
    }

    /**
     * Validate token signature & expiration
     */
    public boolean isTokenValid(String token) {
        try {
            getClaims(token); // Parsing automatically validates token
            return true;
        } catch (Exception e) {
            return false; // Invalid, expired, or tampered token
        }
    }

    /**
     * Internal helper to parse JWT and return claims
     */
    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)   // ✅ Correct API for JJWT 0.12.x
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}

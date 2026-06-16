package com.microservices.apigateway.utility;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JWTUtility {

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expiration;

    /**
     * Convert Secret String -> SecretKey
     */
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(
                secretKey.getBytes()
        );
    }

    /**
     * Generate JWT
     */
    public String generateToken(String username) {

        Date currentDate = new Date();

        Date expiryDate =
                new Date(
                        currentDate.getTime()
                                + expiration
                );

        return Jwts.builder()
                .subject(username)
                .issuedAt(currentDate)
                .expiration(expiryDate)
                .signWith(getSigningKey())
                .compact();
    }

    /**
     * Extract Username
     */
    public String extractUsername(String token) {
        return extractClaim(
                token,
                Claims::getSubject
        );
    }

    /**
     * Extract Expiration Date
     */
    public Date extractExpiration(String token) {
        return extractClaim(
                token,
                Claims::getExpiration
        );
    }

    /**
     * Generic Claim Extractor
     */
    public <T> T extractClaim(
            String token,
            Function<Claims, T> claimsResolver
    ) {

        Claims claims = extractAllClaims(token);

        return claimsResolver.apply(claims);
    }

    /**
     * Extract All Claims
     */
    public Claims extractAllClaims(String token) {

        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Check Expiration
     */
    public boolean isTokenExpired(String token) {

        return extractExpiration(token)
                .before(new Date());
    }

    /**
     * Validate Token
     */
    public boolean validateToken(String token) {

        try {

            return !isTokenExpired(token);

        } catch (Exception ex) {

            return false;
        }
    }
    public String extractRoles(String token) {
        Claims claims = extractAllClaims(token);

        // ✅ "userRoles" — matches the key used in generateToken
        Object userRoles = claims.get("userRoles");
        if (userRoles == null || userRoles.toString().isBlank()) {
            return "";
        };
        return userRoles.toString();
    }
    public String extractUserId(String token) {
        return extractClaim(token, claims -> claims.get("userId", String.class));
    }
}
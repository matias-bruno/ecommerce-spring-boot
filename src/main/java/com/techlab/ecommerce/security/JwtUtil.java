package com.techlab.ecommerce.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 *
 * @author matias-bruno
 */
@Component
public class JwtUtil {

    // Obtener desde application.properties
    @Value("${application.security.jwt.secret-key}")
    private String SECRET;

    @Value("${application.security.jwt.expiration}")
    private Long EXPIRATION;

    private Key getSignKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    public String generateToken(Authentication auth) {
        UserDetails userDetails = (UserDetails)auth.getPrincipal();
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("roles",
                        userDetails.getAuthorities().stream()
                                .map(GrantedAuthority::getAuthority)
                                .toList()
                )
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()
                        + EXPIRATION))
                .signWith(getSignKey())
                .compact();
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }
}

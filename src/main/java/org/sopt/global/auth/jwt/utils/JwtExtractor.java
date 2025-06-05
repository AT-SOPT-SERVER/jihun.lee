package org.sopt.global.auth.jwt.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtExtractor {
    private static final String BEARER = "Bearer ";
    private static final String HEADER_NAME = "Authorization";
    private static final String ID_CLAIM = "id";
    private static final String NICKNAME_CLAIM = "nickname";
    private final Key key;

    public JwtExtractor(@Value("${spring.auth.jwt.key}") String secretKey) {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public Optional<String> extractJwtToken(HttpServletRequest request) {
        String header = request.getHeader(HEADER_NAME);
        if (header == null) {
            return Optional.empty();
        }
        if (header.startsWith(BEARER)) {
            return Optional.of(header.substring(BEARER.length()));
        }
        return Optional.of(header);
    }

    public Long getId(String token){

        return getIdFromToken(token);
    }

    public String getNicknameClaim(String token){

        return getClaimFromToken(token);
    }

    public Boolean isExpired(String token) {
        Claims claims = parseClaims(token);

        return claims.getExpiration().before(new Date());
    }

    private String getClaimFromToken(String token) {
        Claims claims = parseClaims(token);

        return claims.get(JwtExtractor.NICKNAME_CLAIM, String.class);
    }

    private Long getIdFromToken(String token) {
        Claims claims = parseClaims(token);

        return claims.get(JwtExtractor.ID_CLAIM, Long.class);
    }

    private Claims parseClaims(String token) {
        JwtParser parser = Jwts.parserBuilder()
                .setSigningKey(key)
                .build();
        return parser.parseClaimsJws(token).getBody();
    }

    public boolean validateJwtToken(String token) {
        try {
            JwtParser parser = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build();
            parser.parseClaimsJws(token).getBody();

            return true;
        }catch (JwtException e){

            return false;
        }
    }
}

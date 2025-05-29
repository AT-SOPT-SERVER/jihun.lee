package org.sopt.global.auth.jwt.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider {
    public static final String ACCESS_TOKEN_SUBJECT = "Authorization";
    private static final String ID_CLAIM = "id";
    private static final String NICKNAME_CLAIM = "nickname";
    private final Key key;

    @Value("${spring.auth.jwt.accessTokenExpiration}")
    private Long accessTokenExpiration;

    public JwtProvider(@Value("${spring.auth.jwt.key}") String secretKey) {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String generateAccessToken(Long id, String username) {

        return Jwts.builder()
                .claim(ID_CLAIM, id)
                .claim(NICKNAME_CLAIM, username)
                .setSubject(ACCESS_TOKEN_SUBJECT)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpiration))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}

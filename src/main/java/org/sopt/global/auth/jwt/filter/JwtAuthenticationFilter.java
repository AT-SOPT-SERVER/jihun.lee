package org.sopt.global.auth.jwt.filter;

import static org.sopt.global.auth.jwt.exception.ErrorMessage.JWT_TOKEN_EXPIRED;
import static org.sopt.global.auth.jwt.exception.ErrorMessage.JWT_TOKEN_INVALID;
import static org.sopt.global.auth.jwt.exception.ErrorMessage.JWT_TOKEN_NOT_EXIST;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.sopt.global.auth.jwt.utils.JwtExtractor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final static String JWT_ERROR = "jwtError";
    private final JwtExtractor jwtExtractor;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Optional<String> token = jwtExtractor.extractJwtToken(request);

        if (token.isEmpty()) {
            request.setAttribute(JWT_ERROR, JWT_TOKEN_NOT_EXIST);
            filterChain.doFilter(request, response);

            return;
        }
        String accessToken = token.get();

        if (!jwtExtractor.validateJwtToken(accessToken)) {
            request.setAttribute(JWT_ERROR, JWT_TOKEN_INVALID);
            filterChain.doFilter(request, response);

            return;
        }

        if (jwtExtractor.isExpired(accessToken)) {
            request.setAttribute(JWT_ERROR, JWT_TOKEN_EXPIRED);
            filterChain.doFilter(request, response);

            return;
        }

        saveAuthentication(accessToken);
        filterChain.doFilter(request, response);
    }

    private void saveAuthentication(String token) {
        Long id = jwtExtractor.getId(token);
        String nickname = jwtExtractor.getNicknameClaim(token);
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(id, nickname, Collections.emptyList()));
    }
}

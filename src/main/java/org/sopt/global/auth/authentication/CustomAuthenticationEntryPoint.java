package org.sopt.global.auth.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.sopt.global.auth.jwt.exception.ErrorMessage;
import org.sopt.global.common.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements org.springframework.security.web.AuthenticationEntryPoint {
    private final static String LOG_FORMAT = "ExceptionClass: {}, Message: {}";
    private final static String JWT_ERROR = "jwtError";
    private final static String CONTENT_TYPE = "application/json";
    private final static String CHAR_ENCODING = "UTF-8";

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        ErrorMessage jwtError = (ErrorMessage) request.getAttribute(JWT_ERROR);

        if (jwtError != null) {
            setResponse(response, jwtError.getMessage());
            log.error(LOG_FORMAT, jwtError, jwtError.getMessage());
        } else {
            setResponse(response, authException.getMessage());
            log.error(LOG_FORMAT, authException.getClass().getSimpleName(), authException.getMessage());
        }
    }

    private void setResponse(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(CONTENT_TYPE);
        response.setCharacterEncoding(CHAR_ENCODING);

        ApiResponse<Void> body = new ApiResponse<>(
                HttpStatus.UNAUTHORIZED.value(),
                message,
                null
        );
        String json = new ObjectMapper().writeValueAsString(body);

        response.getWriter().write(json);
    }
}

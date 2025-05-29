package org.sopt.global.auth.authorization;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.sopt.global.common.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomAccessDeniedHandler implements org.springframework.security.web.access.AccessDeniedHandler {

    private final static String LOG_FORMAT = "ExceptionClass: {}, Message: {}";
    private final static String CONTENT_TYPE = "application/json";
    private final static String CHAR_ENCODING = "UTF-8";

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, org.springframework.security.access.AccessDeniedException accessDeniedException) throws IOException {
        setResponse(response, accessDeniedException.getMessage());
        log.error(LOG_FORMAT, accessDeniedException.getClass().getSimpleName(), accessDeniedException.getMessage());
    }

    private void setResponse(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType(CONTENT_TYPE);
        response.setCharacterEncoding(CHAR_ENCODING);

        ApiResponse<Void> body = new ApiResponse<>(
                HttpStatus.FORBIDDEN.value(),
                message,
                null
        );
        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
    }
}

package org.sopt.global.common.exception.response;

import org.springframework.http.HttpStatus;

public record ExceptionResponse (
        int code,
        String message
)
{
    public static ExceptionResponse response(HttpStatus status, String message) {
        return new ExceptionResponse(status.value(), message);
    }

    public static ExceptionResponse response(int status, String message) {
        return new ExceptionResponse(status, message);
    }
}

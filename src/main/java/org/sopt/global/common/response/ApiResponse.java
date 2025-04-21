package org.sopt.global.common.response;

import org.springframework.http.HttpStatus;

public class ApiResponse<T> {

    private final int status;
    private final String message;
    private final T data;

    public ApiResponse(int status, String message, T data) {
        this.status  = status;
        this.message = message;
        this.data    = data;
    }

    public ApiResponse(int status, String message) {
        this(status, message, null);
    }

    public static <T> ApiResponse<T> response(
            HttpStatus httpStatus,
            String message,
            T data
    ) {
        return new ApiResponse<>(
                httpStatus.value(),
                message,
                data
        );
    }

    public static <T> ApiResponse<T> response(
            HttpStatus httpStatus,
            String message
    ) {
        return new ApiResponse<>(
                httpStatus.value(),
                message
        );
    }

    public int getStatus() { return status; }

    public String getMessage() { return message; }

    public T getData() { return data; }

}

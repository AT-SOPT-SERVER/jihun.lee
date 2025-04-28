package org.sopt.global.common.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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

    public static <T> ResponseEntity<ApiResponse<T>> response(
            HttpStatus httpStatus,
            String message,
            T data
    ) {
        ApiResponse<T> body = new ApiResponse<>(
                httpStatus.value(),
                message,
                data
        );

        return ResponseEntity.status(httpStatus).body(body);
    }

    public static <T> ResponseEntity<ApiResponse<T>> response(
            HttpStatus httpStatus,
            String message
    ) {
        ApiResponse<T> body = new ApiResponse<>(
                httpStatus.value(),
                message
        );

        return ResponseEntity.status(httpStatus).body(body);
    }

    public int getStatus() { return status; }

    public String getMessage() { return message; }

    public T getData() { return data; }

}

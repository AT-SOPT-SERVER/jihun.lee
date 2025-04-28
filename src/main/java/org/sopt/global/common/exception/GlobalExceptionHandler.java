package org.sopt.global.common.exception;

import java.util.List;
import org.sopt.global.common.exception.response.ExceptionResponse;
import org.sopt.global.common.exception.response.ValidErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ExceptionResponse> handleException(BaseException e) {
        ExceptionResponse body = ExceptionResponse.response(e.getStatus(), e.getMessage());

        return new ResponseEntity<>(body, e.getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ValidErrorResponse>> handleValidation(MethodArgumentNotValidException e) {
        List<ValidErrorResponse> errors = e.getBindingResult()
                .getFieldErrors().stream()
                .map(fe -> ValidErrorResponse.of(
                        fe.getField(),
                        fe.getDefaultMessage(),
                        fe.getRejectedValue()
                ))
                .toList();

        return ResponseEntity
                .badRequest()
                .body(errors);
    }


    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionResponse> handleIllegalArgument(IllegalArgumentException e) {
        ExceptionResponse body = ExceptionResponse.response(HttpStatus.BAD_REQUEST, e.getMessage());

        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ExceptionResponse> handleNoResourceFound(NoResourceFoundException e) {
        ExceptionResponse body = ExceptionResponse.response(HttpStatus.NOT_FOUND, e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleAll(Exception e) {
        ExceptionResponse body = ExceptionResponse.response(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

}

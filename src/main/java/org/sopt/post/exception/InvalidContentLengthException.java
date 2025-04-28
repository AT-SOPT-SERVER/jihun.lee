package org.sopt.post.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import org.sopt.global.common.exception.BaseException;

public class InvalidContentLengthException extends BaseException {
    public InvalidContentLengthException() {
        super(BAD_REQUEST, ErrorMessage.INVALID_CONTENT_LENGTH.getMessage());
    }
}

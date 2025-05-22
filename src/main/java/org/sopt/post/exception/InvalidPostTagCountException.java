package org.sopt.post.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import org.sopt.global.common.exception.BaseException;

public class InvalidPostTagCountException extends BaseException {
    public InvalidPostTagCountException() {
        super(BAD_REQUEST, ErrorMessage.INVALID_POST_TAG_COUNT.getMessage());
    }
}

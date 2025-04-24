package org.sopt.post.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import org.sopt.global.common.exception.BaseException;

public class InvalidTitleLengthException extends BaseException {
    public InvalidTitleLengthException() {
        super(BAD_REQUEST, ErrorMessage.INVALID_TITLE_LENGTH.getMessage());
    }
}

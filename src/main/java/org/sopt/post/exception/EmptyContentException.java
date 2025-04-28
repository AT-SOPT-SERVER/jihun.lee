package org.sopt.post.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import org.sopt.global.common.exception.BaseException;

public class EmptyContentException extends BaseException {
    public EmptyContentException() {
        super(BAD_REQUEST, ErrorMessage.NOT_EMPTY_CONTENT.getMessage());
    }
}

package org.sopt.post.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import org.sopt.global.common.exception.BaseException;

public class EmptyTitleException extends BaseException {
    public EmptyTitleException() {
        super(BAD_REQUEST, ErrorMessage.NOT_EMPTY_TITLE.getMessage());
    }
}

package org.sopt.post.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import org.sopt.global.common.exception.BaseException;

public class DuplicatedTitleException extends BaseException {
    public DuplicatedTitleException() {
        super(BAD_REQUEST, ErrorMessage.DUPLICATED_TITLE.getMessage());
    }
}

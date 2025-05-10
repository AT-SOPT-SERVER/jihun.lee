package org.sopt.post.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import org.sopt.global.common.exception.BaseException;

public class UnauthorizedUpdateException extends BaseException {
    public UnauthorizedUpdateException() {
        super(BAD_REQUEST, ErrorMessage.UNAUTHORIZED_POST_UPDATE.getMessage());
    }
}

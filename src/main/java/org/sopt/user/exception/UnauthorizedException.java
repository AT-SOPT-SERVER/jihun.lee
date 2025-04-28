package org.sopt.user.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import org.sopt.global.common.exception.BaseException;

public class UnauthorizedException extends BaseException {
    public UnauthorizedException() {
        super(BAD_REQUEST, ErrorMessage.UNAUTHORIZED_POST_UPDATE.getMessage());
    }
}

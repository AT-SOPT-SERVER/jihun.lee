package org.sopt.user.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import org.sopt.global.common.exception.BaseException;

public class InvalidNicknameLengthException extends BaseException {
    public InvalidNicknameLengthException() {
        super(BAD_REQUEST, ErrorMessage.INVALID_NICKNAME_LENGTH.getMessage());
    }
}

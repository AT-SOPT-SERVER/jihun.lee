package org.sopt.user.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import org.sopt.global.common.exception.BaseException;

public class EmptyNicknameException extends BaseException {
    public EmptyNicknameException() {
        super(BAD_REQUEST, ErrorMessage.NOT_EMPTY_NICKNAME.getMessage());
    }
}

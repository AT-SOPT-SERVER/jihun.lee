package org.sopt.user.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import org.sopt.global.common.exception.BaseException;

public class DuplicatedNickNameException extends BaseException {
    public DuplicatedNickNameException() {
        super(BAD_REQUEST, ErrorMessage.DUPLICATED_NICKNAME.getMessage());
    }
}

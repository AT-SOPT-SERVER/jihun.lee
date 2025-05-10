package org.sopt.user.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.sopt.global.common.exception.BaseException;

public class UserNotFoundException extends BaseException {
    public UserNotFoundException() {
        super(NOT_FOUND, ErrorMessage.NOT_FOUND_USER.getMessage());
    }
}

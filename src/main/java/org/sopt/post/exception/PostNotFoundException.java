package org.sopt.post.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.sopt.global.common.exception.BaseException;

public class PostNotFoundException extends BaseException {
    public PostNotFoundException() {
        super(NOT_FOUND, ErrorMessage.NOT_FOUND_POST.getMessage());
    }
}

package org.sopt.post.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import org.sopt.global.common.exception.BaseException;

public class PostCreationExceededException extends BaseException {
    public PostCreationExceededException() {
        super(BAD_REQUEST, ErrorMessage.POST_CREATION_INTERVAL_EXCEEDED.getMessage());
    }
}

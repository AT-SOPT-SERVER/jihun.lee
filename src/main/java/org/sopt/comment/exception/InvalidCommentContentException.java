package org.sopt.comment.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import org.sopt.global.common.exception.BaseException;

public class InvalidCommentContentException extends BaseException {
    public InvalidCommentContentException() {
        super(BAD_REQUEST, ErrorMessage.COMMENT_CONTENT_TOO_LONG.getMessage());
    }
}

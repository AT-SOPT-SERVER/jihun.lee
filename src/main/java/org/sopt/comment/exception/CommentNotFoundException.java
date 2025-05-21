package org.sopt.comment.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.sopt.global.common.exception.BaseException;

public class CommentNotFoundException extends BaseException {
    public CommentNotFoundException() {
        super(NOT_FOUND, ErrorMessage.COMMENT_NOT_FOUND.getMessage());
    }
}

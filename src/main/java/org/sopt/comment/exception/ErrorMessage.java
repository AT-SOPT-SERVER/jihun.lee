package org.sopt.comment.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessage {
    COMMENT_NOT_FOUND("존재하지 않는 댓글입니다."),
    COMMENT_CONTENT_TOO_LONG("댓글은 300자를 초과할 수 없습니다.");

    private final String message;
}

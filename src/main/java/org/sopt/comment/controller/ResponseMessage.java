package org.sopt.comment.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessage {
    COMMENT_CREATE_SUCCESS("댓글 작성에 성공했습니다."),
    COMMENT_UPDATE_SUCCESS("댓글 수정에 성공했습니다."),
    COMMENT_DELETE_SUCCESS("댓글 삭제에 성공했습니다.");

    private final String message;
}

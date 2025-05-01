package org.sopt.post.exception;

public enum ErrorMessage {

    INVALID_TITLE_LENGTH("게시글 제목은 30자 이하여야 합니다."),
    INVALID_CONTENT_LENGTH("게시글 내용은 1000자 이하여야 합니다."),
    NOT_EMPTY_TITLE("게시글 제목은 비어있을 수 없습니다."),
    NOT_EMPTY_CONTENT("게시글 내용은 비어있을 수 없습니다."),
    DUPLICATED_TITLE("이미 존재하는 게시글 제목입니다."),
    POST_CREATION_INTERVAL_EXCEEDED("새로운 게시글 작성은 3분 간격으로 가능합니다."),
    NOT_FOUND_POST("해당 ID의 게시글이 존재하지 않습니다."),
    NOT_FOUND_TAG("해당 태그가 존재하지 않습니다."),
    UNAUTHORIZED_POST_UPDATE("다른 사람의 게시글은 수정할 수 없습니다."),
    UNAUTHORIZED_POST_DELETE("다른 사람의 게시글은 삭제할 수 없습니다.");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

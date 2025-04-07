package org.sopt.exception;

public enum ErrorMessage {

    INVALID_TITLE_LENGTH("게시글 제목은 30자 이하여야 합니다."),
    NOT_EMPTY_TITLE("게시글 제목은 비어있을 수 없습니다."),
    DUPLICATED_TITLE("이미 존재하는 게시글 제목입니다."),
    POST_CREATION_INTERVAL_EXCEEDED("새로운 게시글 작성은 3분 간격으로 가능합니다.");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

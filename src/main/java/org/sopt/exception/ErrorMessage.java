package org.sopt.exception;

public enum ErrorMessage {

    INVALID_TITLE_LENGTH("게시글 제목은 30자 이하여야 합니다."),
    NOT_EMPTY_TITLE("게시글 제목은 비어있을 수 없습니다."),
    DUPLICATED_TITLE("이미 존재하는 게시글 제목입니다.");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

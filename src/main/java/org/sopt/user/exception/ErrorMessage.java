package org.sopt.user.exception;

public enum ErrorMessage {

    NOT_EMPTY_NICKNAME("닉네임은 비어있을 수 없습니다."),
    DUPLICATED_NICKNAME("이미 존재하는 닉네임입니다."),
    INVALID_NICKNAME_LENGTH("닉네임은 10자 이하여야 합니다."),
    NOT_FOUND_USER("해당 ID의 유저가 존재하지 않습니다.");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}

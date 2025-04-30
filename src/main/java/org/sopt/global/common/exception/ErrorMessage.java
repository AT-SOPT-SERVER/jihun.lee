package org.sopt.global.common.exception;

public enum ErrorMessage {

    JSON_PARSE_ERROR("잘못된 JSON 형식의 요청입니다.");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}

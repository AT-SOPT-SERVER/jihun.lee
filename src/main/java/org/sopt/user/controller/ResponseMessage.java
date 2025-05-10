package org.sopt.user.controller;

public enum ResponseMessage {

    USER_CREATE_SUCCESS("회원가입에 성공했습니다.");

    private final String message;

    ResponseMessage(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }

}

package org.sopt.user.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessage {

    USER_CREATE_SUCCESS("회원가입에 성공했습니다.");

    private final String message;
}

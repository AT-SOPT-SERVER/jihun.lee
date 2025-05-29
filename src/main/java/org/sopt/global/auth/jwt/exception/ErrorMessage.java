package org.sopt.global.auth.jwt.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessage {
    JWT_TOKEN_NOT_FOUND("토큰을 찾을 수 없습니다"),
    JWT_TOKEN_EXPIRED("만료된 토큰입니다."),
    JWT_TOKEN_INVALID("유효하지 않은 토큰 입니다."),
    JWT_TOKEN_NOT_EXIST("헤더에 인증 토큰이 존재하지 않습니다");

    private final String message;
}

package org.sopt.global.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessage {
    JSON_PARSE_ERROR("잘못된 JSON 형식의 요청입니다.");

    private final String message;
}

package org.sopt.global.common.cache.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessage {
    CACHE_NOT_FOUND("찾을 수 없는 캐시입니다");

    private final String message;
}

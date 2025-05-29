package org.sopt.like.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessage {
    LIKE_TOGGLE_SUCCESS("좋아요 토글에 성공했습니다."),
    LIKE_COUNT_SUCCESS("좋아요 수 조회에 성공했습니다."),
    LIKE_GET_USERS_SUCCESS("좋아요를 누른 사용자 목록 조회에 성공했습니다.");

    private final String message;
}

package org.sopt.global.auth.jwt.dto;

import lombok.Builder;

@Builder
public record JwtTokenDto(
        String accessToken
) {
    public static JwtTokenDto of(String accessToken) {
        return JwtTokenDto.builder()
                .accessToken(accessToken)
                .build();
    }
}

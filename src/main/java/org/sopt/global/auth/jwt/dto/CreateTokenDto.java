package org.sopt.global.auth.jwt.dto;

import lombok.Builder;
import org.sopt.user.domain.User;

@Builder
public record CreateTokenDto(
        Long id,
        String nickname
) {
    public static CreateTokenDto from(User user){
        return CreateTokenDto.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .build();
    }
}

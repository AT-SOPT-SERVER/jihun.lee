package org.sopt.user.dto.response;

public record LoginResponse(
        Long userId,
        String accessToken
) {
    public static LoginResponse of(Long userId, String token) {
        return new LoginResponse(userId, token);
    }
}

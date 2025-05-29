package org.sopt.like.dto.response;

public record LikeCountResponse(
    long count
) {
    public static LikeCountResponse of(long count) {
        return new LikeCountResponse(count);
    }
}

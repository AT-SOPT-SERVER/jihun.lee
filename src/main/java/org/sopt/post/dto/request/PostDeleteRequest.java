package org.sopt.post.dto.request;

public class PostDeleteRequest {

    public record Delete(
            Long userId,
            Long postId
    ) {
    }

}

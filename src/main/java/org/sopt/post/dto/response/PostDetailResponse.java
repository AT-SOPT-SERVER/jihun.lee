package org.sopt.post.dto.response;

import org.sopt.post.domain.Post;

public class PostDetailResponse {

    public record Detail(
            Long id,
            String title,
            String content,
            String author,
            String tag
    ) {
        public static Detail of(Post post) {
            return new Detail(
                    post.getId(),
                    post.getTitle(),
                    post.getContent(),
                    post.getAuthor().getNickname(),
                    post.getTag() != null ? post.getTag().getValue() : null
            );
        }
    }

}

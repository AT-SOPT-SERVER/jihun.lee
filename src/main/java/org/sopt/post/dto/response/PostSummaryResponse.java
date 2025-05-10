package org.sopt.post.dto.response;

import org.sopt.post.domain.Post;

public class PostSummaryResponse {

    public record Summary(
            Long id,
            String title,
            String author
    ) {
        public static Summary of(Post post) {
            return new Summary(
                    post.getId(),
                    post.getTitle(),
                    post.getAuthor().getNickname()
            );
        }
    }

}

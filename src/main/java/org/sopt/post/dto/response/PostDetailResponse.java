package org.sopt.post.dto.response;

import java.util.List;
import org.sopt.comment.dto.response.CommentResponse;
import org.sopt.post.domain.Post;
import org.sopt.post.domain.enums.Tags;

    public class PostDetailResponse {

        public record Detail(
                Long id,
                String title,
                String content,
                String author,
                List<String> tags,
                List<CommentResponse.Detail> comments
        ) {
            public static Detail of(Post post) {
                return new Detail(
                        post.getId(),
                        post.getTitle(),
                        post.getContent(),
                        post.getAuthor().getNickname(),
                        post.getTags().stream()
                                .map(Tags::getValue)
                                .toList(),
                        post.getComments().stream()
                                .map(CommentResponse.Detail::of)
                                .toList()
                );
            }
        }
    }

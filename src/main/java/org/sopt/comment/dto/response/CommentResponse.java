package org.sopt.comment.dto.response;

import java.time.LocalDateTime;
import org.sopt.comment.domain.Comment;

public class CommentResponse {

    public record Detail(
            Long id,
            String content,
            LocalDateTime createdAt,
            LocalDateTime modifiedAt
    ) {
        public static Detail of(Comment comment) {
            return new Detail(
                    comment.getId(),
                    comment.getContent(),
                    comment.getCreatedAt(),
                    comment.getModifiedAt()
            );
        }
    }
}

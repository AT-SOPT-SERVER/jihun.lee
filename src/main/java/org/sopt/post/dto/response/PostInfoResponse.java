package org.sopt.post.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import org.sopt.post.domain.Post;

public record PostInfoResponse(
        Long id,
        String title,
        String content,
        String authorName,
        List<String> tags,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {
    public static PostInfoResponse from(Post post) {
        return new PostInfoResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getAuthor().getNickname(),
                post.getTags().stream().map(Enum::name).toList(),
                post.getCreatedAt(),
                post.getModifiedAt()
        );
    }
}

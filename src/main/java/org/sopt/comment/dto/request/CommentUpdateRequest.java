package org.sopt.comment.dto.request;

import static org.sopt.global.utils.CommentDtoValidator.validateContent;

import jakarta.validation.constraints.NotBlank;

public class CommentUpdateRequest {
    public record Update(
            @NotBlank(message = "댓글 내용은 비어있을 수 없습니다.") String content
    ) {
        public Update {
            validateContent(content);
        }
    }
}

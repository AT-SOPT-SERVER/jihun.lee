package org.sopt.comment.dto.request;

import static org.sopt.global.utils.CommentDtoValidator.validateContent;

import jakarta.validation.constraints.NotBlank;

public class CommentCreateRequest {
    public record Create(
            @NotBlank(message = "댓글 내용은 비어있을 수 없습니다.") String content
    ) {
        public Create {
            validateContent(content);
        }
    }
}

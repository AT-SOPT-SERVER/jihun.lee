package org.sopt.post.dto.request;

import static org.sopt.global.utils.PostDtoValidator.validateContentStructure;
import static org.sopt.global.utils.PostDtoValidator.validateTitleStructure;

import jakarta.validation.constraints.NotBlank;

public class PostCreateRequest {

    public record Create(
            @NotBlank(message = "제목은 비어있을 수 없습니다.") String title,
            @NotBlank(message = "내용은 비어있을 수 없습니다.") String content,
            String tag
    ){
        public Create {
            validateTitleStructure(title);
            validateContentStructure(content);
        }
    }
}

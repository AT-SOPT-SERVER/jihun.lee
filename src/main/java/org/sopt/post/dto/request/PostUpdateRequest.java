package org.sopt.post.dto.request;

import static org.sopt.global.utils.PostDtoValidator.validateContentStructure;
import static org.sopt.global.utils.PostDtoValidator.validateTagStructure;
import static org.sopt.global.utils.PostDtoValidator.validateTitleStructure;

import jakarta.validation.constraints.NotBlank;
import java.util.List;

public class PostUpdateRequest {

    public record Update(
            @NotBlank(message = "제목은 비어있을 수 없습니다.") String newTitle,
            @NotBlank(message = "내용은 비어있을 수 없습니다.") String newContent,
            List<String> newTags
    ) {
        public Update {
            validateTitleStructure(newTitle);
            validateContentStructure(newContent);
            validateTagStructure(newTags);
        }
    }
}

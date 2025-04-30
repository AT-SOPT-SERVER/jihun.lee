package org.sopt.post.dto.request;

import static org.sopt.global.utils.PostDtoValidator.validateContentStructure;
import static org.sopt.global.utils.PostDtoValidator.validateTitleStructure;

import com.fasterxml.jackson.annotation.JsonCreator;

public class PostUpdateRequest {

    public record Update(
            String newTitle,
            String newContent,
            String newTag
    ) {
        @JsonCreator
        public Update {
            if (newTitle != null) {
                validateTitleStructure(newTitle);
            }
            if (newContent != null) {
                validateContentStructure(newContent);
            }
        }
    }

}

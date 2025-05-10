package org.sopt.post.dto.request;

import static org.sopt.global.utils.PostDtoValidator.validateContentStructure;
import static org.sopt.global.utils.PostDtoValidator.validateTitleStructure;

public class PostUpdateRequest {

    public record Update(
            String newTitle,
            String newContent,
            String newTag
    ) {
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

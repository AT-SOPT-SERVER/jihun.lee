package org.sopt.post.dto.request;

import static org.sopt.global.utils.PostDtoValidator.validateContentStructure;
import static org.sopt.global.utils.PostDtoValidator.validateTitleStructure;

public class PostCreateRequest {

    public record Create(
            String title,
            String content,
            String tag
    ){
        public Create {
            validateTitleStructure(title);
            validateContentStructure(content);
        }
    }

}

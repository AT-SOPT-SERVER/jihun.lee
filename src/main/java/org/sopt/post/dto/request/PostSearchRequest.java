package org.sopt.post.dto.request;

import static org.sopt.global.utils.PostDtoValidator.validateTagStructure;

import java.util.Collections;
import java.util.List;

public class PostSearchRequest {

    public record Search(
            String keyword,
            List<String> tags,
            int page,
            int size
    ) {
        public static Search of(String keyword, List<String> tags, int page, int size) {
            List<String> safeTags = tags != null ? tags : Collections.emptyList();
            validateTagStructure(safeTags);
            return new Search(keyword, safeTags, page, size);
        }
    }
}

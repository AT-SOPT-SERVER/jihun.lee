package org.sopt.post.dto.request;

public class PostSearchRequest {

    public record Search(
            String keyword,
            String tag
    ) {
        public static PostSearchRequest.Search of(String keyword, String tag) {
            return new PostSearchRequest.Search(keyword, tag);
        }
    }

}

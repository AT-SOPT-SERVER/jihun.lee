package org.sopt.post.dto.response;

import java.util.List;
import org.sopt.post.domain.Post;
import org.springframework.data.domain.Page;

public record PostInfoListResponse(
        List<PostInfoResponse> content,
        PageableInfo pageInfo
) {
    public static PostInfoListResponse from(Page<Post> posts) {
        List<PostInfoResponse> dtoList = posts
                .map(PostInfoResponse::from)
                .getContent();
        PageableInfo pageInfo = PageableInfo.of(posts);

        return new PostInfoListResponse(dtoList, pageInfo);
    }
}

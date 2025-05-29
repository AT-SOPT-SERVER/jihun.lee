package org.sopt.like.dto.response;

import java.util.List;
import org.sopt.global.common.response.PageableInfo;
import org.springframework.data.domain.Page;

public record LikersPageResponse (
        List<String> content,
        PageableInfo pageInfo
) {
    public static LikersPageResponse of(Page<String> page) {
        return new LikersPageResponse(
                page.getContent(),
                PageableInfo.of(page)
        );
    }
}

package org.sopt.post.dto.response;

import java.util.List;
import org.sopt.post.dto.response.PostSummaryResponse.Summary;

public record PostPageResponse (
        List<Summary> content,
        PageableInfo pageInfo
){}

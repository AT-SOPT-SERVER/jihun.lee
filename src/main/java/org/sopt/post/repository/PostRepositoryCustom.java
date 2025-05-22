package org.sopt.post.repository;

import java.util.List;
import org.sopt.post.domain.Post;
import org.sopt.post.domain.enums.Tags;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostRepositoryCustom {
    Page<Post> search(String keyword, List<Tags> tags, Pageable pageable);
}

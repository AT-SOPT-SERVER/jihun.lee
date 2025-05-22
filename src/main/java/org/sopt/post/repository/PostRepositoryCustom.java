package org.sopt.post.repository;

import java.util.List;
import org.sopt.post.domain.Post;
import org.sopt.post.domain.enums.Tags;

public interface PostRepositoryCustom {
    List<Post> search(String keyword, List<Tags> tags);
}

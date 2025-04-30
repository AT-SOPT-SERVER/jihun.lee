package org.sopt.post.repository;

import java.util.List;
import java.util.Optional;
import org.sopt.post.domain.Post;
import org.sopt.post.domain.enums.Tags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    boolean existsByTitle(String title);

    List<Post> findAllByTitleContainingIgnoreCaseOrAuthorNicknameContainingIgnoreCase(String titleKeyword, String authorKeyword);

    List<Post> findAllByTags(Tags tag);

    @Query("select p from Post p join p.author a where p.tags = :tag and (lower(p.title) like lower(concat('%', :keyword, '%')) or lower(a.nickname) like lower(concat('%', :keyword, '%')))")
    List<Post> searchByKeywordAndTag(@Param("keyword") String keyword, @Param("tag") Tags tag);

    Optional<Post> findFirstByAuthorIdOrderByModifiedAtDesc(Long authorId);

    List<Post> findAllByOrderByModifiedAtDesc();

}

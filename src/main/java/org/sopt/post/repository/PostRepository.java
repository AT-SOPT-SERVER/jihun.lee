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
public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {
    boolean existsByTitle(String title);

    @Query("""
    SELECT p
      FROM Post p
      JOIN p.tags t
     WHERE t IN :tags
    GROUP BY p.id
    HAVING COUNT(DISTINCT t) = :#{#tags.size()}
    """)
    List<Post> findByTagsIn(@Param("tags") List<Tags> tags);

    List<Post> findAllByTitleContainingIgnoreCaseOrAuthorNicknameContainingIgnoreCase(String titleKeyword, String authorKeyword);

    Optional<Post> findFirstByAuthorIdOrderByModifiedAtDesc(Long authorId);

    @Query("select p from Post p join fetch p.author where p.id = :id")
    Optional<Post> findByIdWithAuthor(@Param("id") Long id);
}

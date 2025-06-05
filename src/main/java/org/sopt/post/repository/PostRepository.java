package org.sopt.post.repository;

import java.util.Optional;
import org.sopt.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {
    boolean existsByTitle(String title);

    Optional<Post> findFirstByAuthorIdOrderByModifiedAtDesc(Long authorId);

    @Query("select p from Post p join fetch p.author where p.id = :id")
    Optional<Post> findByIdWithAuthor(@Param("id") Long id);
}

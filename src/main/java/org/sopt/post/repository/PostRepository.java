package org.sopt.post.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.sopt.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    boolean existsByTitle(String title);

    List<Post> findAllByTitleContaining(String keyword);

    @Query("select p.modifiedAt from Post p order by p.modifiedAt desc")
    Optional<LocalDateTime> findLatestModifiedAt();

}

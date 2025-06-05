package org.sopt.like.repository;

import java.util.Optional;
import org.sopt.like.domain.PostLike;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike,Long> {
    Optional<PostLike> findByPostIdAndUserId(Long postId, Long userId);
    long countByPostId(Long postId);

    @Query("""
      select u.nickname
        from PostLike pl
        join User u           on u.id = pl.userId
       where pl.postId = :postId
    """)
    Page<String> findNicknamesByPostId(@Param("postId") Long postId, Pageable pageable);
}

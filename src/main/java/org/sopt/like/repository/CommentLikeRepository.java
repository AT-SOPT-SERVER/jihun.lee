package org.sopt.like.repository;

import org.sopt.like.domain.CommentLike;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    boolean existsByCommentIdAndUserId(Long commentId, Long userId);
    void deleteByCommentIdAndUserId(Long commentId, Long userId);
    long countByCommentId(Long commentId);

    @Query("""
      select u.nickname
        from CommentLike pl
        join User u           on u.id = pl.userId
       where pl.commentId = :commentId
    """)
    Page<String> findNicknamesByCommentId(@Param("commentId") Long commentId, Pageable pageable);
}

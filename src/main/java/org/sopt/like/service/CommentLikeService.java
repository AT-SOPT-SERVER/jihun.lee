package org.sopt.like.service;

import lombok.RequiredArgsConstructor;
import org.sopt.comment.exception.CommentNotFoundException;
import org.sopt.comment.repository.CommentRepository;
import org.sopt.global.common.aop.lock.DistributedLock;
import org.sopt.like.domain.CommentLike;
import org.sopt.like.dto.response.LikersPageResponse;
import org.sopt.like.repository.CommentLikeRepository;
import org.sopt.user.exception.UserNotFoundException;
import org.sopt.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentLikeService {
    private final CommentLikeRepository commentLikeRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @DistributedLock(key = "'comment:' + #commentId+ ':user:' + #userId")
    public void toggleCommentLike(Long commentId, Long userId) {
        if (!commentRepository.existsById(commentId)) {
            throw new CommentNotFoundException();
        }
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException();
        }
        boolean exists = commentLikeRepository.existsByCommentIdAndUserId(commentId, userId);
        if (exists) {
            commentLikeRepository.deleteByCommentIdAndUserId(commentId, userId);
        } else {
            commentLikeRepository.save(CommentLike.builder()
                    .commentId(commentId)
                    .userId(userId)
                    .build());
        }
    }

    @Transactional(readOnly = true)
    public long getCommentLikeCount(Long commentId) {
        if (!commentRepository.existsById(commentId)) {
            throw new CommentNotFoundException();
        }

        return commentLikeRepository.countByCommentId(commentId);
    }

    @Transactional(readOnly = true)
    public LikersPageResponse getCommentLikers(Long commentId, int page, int size) {
        if (!commentRepository.existsById(commentId)) {
            throw new CommentNotFoundException();
        }
        Page<String> nicknames = commentLikeRepository.findNicknamesByCommentId(commentId, PageRequest.of(page, size, Sort.by("id").descending()));

        return LikersPageResponse.of(nicknames);
    }
}

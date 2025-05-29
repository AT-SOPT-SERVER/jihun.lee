package org.sopt.like.service;

import lombok.RequiredArgsConstructor;
import org.sopt.global.config.aop.lock.DistributedLock;
import org.sopt.like.domain.PostLike;
import org.sopt.like.dto.response.LikersPageResponse;
import org.sopt.like.repository.PostLikeRepository;
import org.sopt.post.exception.PostNotFoundException;
import org.sopt.post.repository.PostRepository;
import org.sopt.user.exception.UserNotFoundException;
import org.sopt.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostLikeService {
    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @DistributedLock(key = "'post:' + #postId + ':user:' + #userId")
    public void togglePostLike(Long postId, Long userId) {
        if (!postRepository.existsById(postId)) {
            throw new PostNotFoundException();
        }
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException();
        }
        boolean exists = postLikeRepository.existsByPostIdAndUserId(postId, userId);
        if (exists) {
            postLikeRepository.deleteByPostIdAndUserId(postId, userId);
        } else {
            postLikeRepository.save(PostLike.builder()
                    .postId(postId)
                    .userId(userId)
                    .build());
        }
    }

    @Transactional(readOnly = true)
    public long getPostLikeCount(Long postId) {
        if (!postRepository.existsById(postId)) {
            throw new PostNotFoundException();
        }

        return postLikeRepository.countByPostId(postId);
    }

    @Transactional(readOnly = true)
    public LikersPageResponse getPostLikers(Long postId, int page, int size) {
        if (!postRepository.existsById(postId)) {
            throw new PostNotFoundException();
        }
        Page<String> nicknames = postLikeRepository.findNicknamesByPostId(postId, PageRequest.of(page, size, Sort.by("id").descending()));

        return LikersPageResponse.of(nicknames);
    }

}

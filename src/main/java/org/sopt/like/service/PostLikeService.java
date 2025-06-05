package org.sopt.like.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.sopt.global.common.aop.lock.DistributedLock;
import org.sopt.like.domain.PostLike;
import org.sopt.like.dto.response.LikersPageResponse;
import org.sopt.like.repository.PostLikeRepository;
import org.sopt.post.domain.Post;
import org.sopt.post.exception.PostNotFoundException;
import org.sopt.post.repository.PostRepository;
import org.sopt.user.domain.User;
import org.sopt.user.exception.UserNotFoundException;
import org.sopt.user.repository.UserRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
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
    @Caching(evict = {
            @CacheEvict(cacheNames = "post_likes_count", key = "#postId"),
            @CacheEvict(cacheNames = "post_likes_users", allEntries = true)
    })
    public void togglePostLike(Long postId, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        processPostLike(post, user);
    }

    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "post_likes_count", key = "#postId")
    public long getPostLikeCount(Long postId) {
        if (!postRepository.existsById(postId)) {
            throw new PostNotFoundException();
        }

        return postLikeRepository.countByPostId(postId);
    }

    @Transactional(readOnly = true)
    @Cacheable(
            cacheNames = "post_likes_users",
            key = "'post:' + #postId + ':page:' + #page + ':size:' + #size"
    )
    public LikersPageResponse getPostLikers(Long postId, int page, int size) {
        if (!postRepository.existsById(postId)) {
            throw new PostNotFoundException();
        }
        Page<String> nicknames = postLikeRepository.findNicknamesByPostId(postId, PageRequest.of(page, size, Sort.by("id").descending()));

        return LikersPageResponse.of(nicknames);
    }

    @Transactional
    public void processPostLike(Post post, User user) {
        Long postId = post.getId();
        Long userId = user.getId();
        Optional<PostLike> existingLike =
                postLikeRepository.findByPostIdAndUserId(postId, userId);

        if (existingLike.isPresent()) {
            postLikeRepository.delete(existingLike.get());
        } else {
            PostLike newLike = new PostLike(postId, userId);
            postLikeRepository.save(newLike);
        }
    }
}

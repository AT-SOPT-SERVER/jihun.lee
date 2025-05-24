package org.sopt.post.service;

import static org.sopt.global.utils.PostCreationIntervalValidator.validateCreationInterval;

import io.micrometer.common.util.StringUtils;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.sopt.post.domain.Post;
import org.sopt.post.domain.enums.Tags;
import org.sopt.post.dto.request.PostCreateRequest;
import org.sopt.post.dto.request.PostDeleteRequest;
import org.sopt.post.dto.request.PostSearchRequest;
import org.sopt.post.dto.request.PostUpdateRequest;
import org.sopt.post.dto.response.PageableInfo;
import org.sopt.post.dto.response.PostDetailResponse;
import org.sopt.post.dto.response.PostPageResponse;
import org.sopt.post.dto.response.PostSummaryResponse;
import org.sopt.post.exception.DuplicatedTitleException;
import org.sopt.post.exception.PostNotFoundException;
import org.sopt.post.exception.UnauthorizedDeleteException;
import org.sopt.post.exception.UnauthorizedUpdateException;
import org.sopt.post.repository.PostRepository;
import org.sopt.user.domain.User;
import org.sopt.user.exception.UserNotFoundException;
import org.sopt.user.repository.UserRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    @CacheEvict(cacheNames = "posts_page", allEntries = true)
    public void createPost(PostCreateRequest.Create dto, final Long userId) {
        User author = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        if (postRepository.existsByTitle(dto.title())) {
            throw new DuplicatedTitleException();
        }

        LocalDateTime latestModifiedAtForUser = postRepository
                .findFirstByAuthorIdOrderByModifiedAtDesc(userId)
                .map(Post::getModifiedAt)
                .orElse(null);
        validateCreationInterval(latestModifiedAtForUser);

        List<Tags> tagEnums = Tags.toList(dto.tags());

        Post post = new Post(dto.title(), dto.content(), tagEnums, author);
        postRepository.save(post);
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(cacheNames = "posts_page", allEntries = true),
            @CacheEvict(cacheNames = "post_detail", key = "#postId")
    })
    public void updatePost(final Long userId, final Long postId, PostUpdateRequest.Update dto) {
        Post post = postRepository.findByIdWithAuthor(postId)
                .orElseThrow(PostNotFoundException::new);

        if (!post.getAuthor().getId().equals(userId)) {
            throw new UnauthorizedUpdateException();
        }

        post.updatePost(dto.newTitle(), dto.newContent(), Tags.toList(dto.newTags()));

        postRepository.save(post);
    }

    @GetMapping
    @Cacheable(cacheNames = "posts_page", key = "'posts_page:' + #page + ':' + #size")
    public PostPageResponse getAllPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("modifiedAt").descending());
        Page<Post> postPage = postRepository.findAll(pageable);

        List<PostSummaryResponse.Summary> summaries =
                postPage.stream()
                        .map(PostSummaryResponse.Summary::of)
                        .toList();

        PageableInfo pageInfo = PageableInfo.of(postPage);
        return new PostPageResponse(summaries, pageInfo);
    }

    @GetMapping("/{id}")
    @Cacheable(cacheNames = "post_detail", key = "#id")
    public PostDetailResponse.Detail getPostById(final Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFoundException::new);

        return PostDetailResponse.Detail.of(post);
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(cacheNames = "posts_page", allEntries = true),
            @CacheEvict(cacheNames = "post_detail", key = "#dto.postId()")
    })
    public void deletePostById(PostDeleteRequest.Delete dto) {
        Post post = postRepository.findByIdWithAuthor(dto.postId())
                .orElseThrow(PostNotFoundException::new);

        if (!post.getAuthor().getId().equals(dto.userId())) {
            throw new UnauthorizedDeleteException();
        }

        postRepository.deleteById(dto.postId());
    }

    public Page<Post> searchPosts(PostSearchRequest.Search dto) {
        String keyword  = dto.keyword();
        List<Tags> tagEnums = dto.tags().stream()
                .filter(s -> !s.isBlank())
                .map(Tags::to)
                .toList();
        Pageable pageable = PageRequest.of(dto.page(), dto.size(), Sort.by("createdAt").descending());
        if (StringUtils.isBlank(keyword) && tagEnums.isEmpty()) {
            return Page.empty(pageable);
        }

        return postRepository.search(keyword, tagEnums, pageable);
    }
}

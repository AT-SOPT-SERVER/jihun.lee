package org.sopt.post.service;

import static org.sopt.global.utils.PostCreationIntervalValidator.validateCreationInterval;

import java.time.LocalDateTime;
import java.util.List;
import org.sopt.post.domain.Post;
import org.sopt.post.domain.enums.Tags;
import org.sopt.post.dto.request.PostCreateRequest;
import org.sopt.post.dto.request.PostDeleteRequest;
import org.sopt.post.dto.request.PostSearchRequest;
import org.sopt.post.dto.request.PostUpdateRequest;
import org.sopt.post.dto.response.PostDetailResponse;
import org.sopt.post.dto.response.PostSummaryResponse;
import org.sopt.post.exception.DuplicatedTitleException;
import org.sopt.post.exception.PostNotFoundException;
import org.sopt.post.exception.UnauthorizedDeleteException;
import org.sopt.post.exception.UnauthorizedUpdateException;
import org.sopt.post.repository.PostRepository;
import org.sopt.user.domain.User;
import org.sopt.user.exception.UserNotFoundException;
import org.sopt.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository){
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Transactional
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

        Tags tagEnum = dto.tag() != null ? Tags.to(dto.tag()) : null;

        Post post = new Post(dto.title(), dto.content(), tagEnum, author);
        postRepository.save(post);
    }

    @Transactional
    public void updatePost(final Long userId, final Long postId, PostUpdateRequest.Update dto) {
        User author = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        if (!post.getAuthor().equals(author)) {
            throw new UnauthorizedUpdateException();
        }

        if (dto.newTitle() != null) {
            post.updateTitle(dto.newTitle());
        }
        if (dto.newContent() != null) {
            post.updateContent(dto.newContent());
        }
        if (dto.newTag() != null) {
            Tags newTag = Tags.to(dto.newTag());
            post.updateTags(newTag);
        }

        postRepository.save(post);
    }

    public List<PostSummaryResponse.Summary> getAllPosts() {

        return postRepository.findAllByOrderByModifiedAtDesc()
                .stream()
                .map(PostSummaryResponse.Summary::of)
                .toList();
    }

    public PostDetailResponse.Detail getPostById(final Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFoundException::new);

        return PostDetailResponse.Detail.of(post);
    }

    @Transactional
    public void deletePostById(PostDeleteRequest.Delete dto) {
        User author = userRepository.findById(dto.userId())
                .orElseThrow(UserNotFoundException::new);

        Post post = postRepository.findById(dto.postId())
                .orElseThrow(PostNotFoundException::new);

        if (!post.getAuthor().equals(author)) {
            throw new UnauthorizedDeleteException();
        }

        postRepository.deleteById(dto.postId());
    }

    public List<Post> searchPosts(PostSearchRequest.Search dto) {
        String keyword = dto.keyword();
        String tag     = dto.tag();

        if (keyword == null && tag == null) {
            return List.of();
        }

        if (keyword == null) {
            return Tags.from(tag)
                    .map(postRepository::findAllByTags)
                    .orElse(List.of());
        }

        if (tag == null) {
            return postRepository
                    .findAllByTitleContainingIgnoreCaseOrAuthorNicknameContainingIgnoreCase(keyword, keyword);
        }

        return Tags.from(tag)
                .map(t -> postRepository.searchByKeywordAndTag(keyword, t))
                .orElse(List.of());
    }
}

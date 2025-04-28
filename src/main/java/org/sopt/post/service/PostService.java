package org.sopt.post.service;

import java.time.LocalDateTime;
import java.util.List;
import org.sopt.post.domain.Post;
import org.sopt.post.domain.enums.Tags;
import org.sopt.post.dto.PostRequestDto;
import org.sopt.post.exception.PostNotFoundException;
import org.sopt.post.repository.PostRepository;
import org.sopt.user.domain.User;
import org.sopt.user.exception.UnauthorizedException;
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
    public void createPost(PostRequestDto.Create dto, Long userId) {
        User author = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        PostValidator.validateTitle(dto.title(), postRepository);
        PostValidator.validateContent(dto.content());

        LocalDateTime latestModifiedAtForUser = postRepository
                .findFirstByAuthorIdOrderByModifiedAtDesc(userId)
                .map(Post::getModifiedAt)
                .orElse(null);
        PostValidator.validateCreationInterval(latestModifiedAtForUser);

        Tags tagEnum = dto.tag() != null
                ? Tags.to(dto.tag())
                : null;

        Post post = new Post(dto.title(), dto.content(), tagEnum, author);

        postRepository.save(post);
    }

    @Transactional
    public void updatePost(Long userId, Long postId, PostRequestDto.Update dto) {
        User author = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        if (!post.getAuthor().equals(author)) {
            throw new UnauthorizedException();
        }

        if (dto.newTitle() != null) {
            PostValidator.validateTitle(dto.newTitle(), postRepository);
            post.updateTitle(dto.newTitle());
        }

        if (dto.newContent() != null) {
            PostValidator.validateContent(dto.newContent());
            post.updateContent(dto.newContent());
        }

        if (dto.newTag() != null) {
            Tags newTag = Tags.to(dto.newTag());
            post.updateTags(newTag);
        }

        postRepository.save(post);
    }

    public List<Post> getAllPosts() {

        return postRepository.findAll();
    }

    public Post getPostById(Long id) {

        return postRepository.findById(id)
                .orElseThrow(PostNotFoundException::new);
    }

    @Transactional
    public void deletePostById(PostRequestDto.Delete dto) {
        if (!postRepository.existsById(dto.id())) {
            throw new PostNotFoundException();
        }

        postRepository.deleteById(dto.id());
    }

    public List<Post> searchPosts(PostRequestDto.Search dto) {

        return postRepository.findAllByTitleContaining(dto.keyword());
    }

}

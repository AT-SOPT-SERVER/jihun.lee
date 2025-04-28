package org.sopt.post.service;

import java.time.LocalDateTime;
import java.util.List;
import org.sopt.post.domain.Post;
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
    public Post createPost(PostRequestDto.Create dto, Long userId) {
        User author = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        PostValidator.validateTitle(dto.title(), postRepository);
        PostValidator.validateContent(dto.content());

        LocalDateTime latestModifiedAt = postRepository.findLatestModifiedAt().orElse(null);
        PostValidator.validateCreationInterval(latestModifiedAt);

        Post post = new Post(dto.title(), dto.content(), author);

        return postRepository.save(post);
    }

    @Transactional
    public Post updatePost(Long userId, Long postId, PostRequestDto.Update dto) {
        User author = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        if (!post.getAuthor().equals(author)) {
            throw new UnauthorizedException();
        }

        PostValidator.validateTitle(dto.newTitle(), postRepository);
        post.updateTitle(dto.newTitle());

        PostValidator.validateContent(dto.newContent());
        post.updateContent(dto.newContent());

        return postRepository.save(post);
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

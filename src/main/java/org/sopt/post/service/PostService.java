package org.sopt.post.service;

import static org.sopt.post.exception.ErrorMessage.NOT_FOUND_POST;

import java.time.LocalDateTime;
import java.util.List;
import org.sopt.post.domain.Post;
import org.sopt.post.dto.PostRequestDto;
import org.sopt.post.repository.PostRepository;
import org.sopt.global.utils.PostValidator;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    private LocalDateTime updatedAt;

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository){
        this.postRepository = postRepository;
    }

    public Post createPost(PostRequestDto.Create dto) {
        PostValidator.validateTitle(dto.title(), postRepository);
        PostValidator.validateCreationInterval(updatedAt);

        Post post = new Post(dto.title());

        Post savedPost = postRepository.save(post);
        updatedAt = LocalDateTime.now();

        return savedPost;
    }

    public Post updatePostTitle(PostRequestDto.Update dto) {
        Post post = postRepository.findById(dto.id())
                .orElseThrow(() -> new IllegalArgumentException(NOT_FOUND_POST.getMessage()));

        PostValidator.validateTitle(dto.newTitle(), postRepository);
        post.updateTitle(dto.newTitle());

        return postRepository.save(post);
    }

    public List<Post> getAllPosts() {

        return postRepository.findAll();
    }

    public Post getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(NOT_FOUND_POST.getMessage()));
    }


    public boolean deletePostById(PostRequestDto.Delete dto) {
        if (!postRepository.existsById(dto.id())) {
            throw new IllegalArgumentException(NOT_FOUND_POST.getMessage());
        }
        postRepository.deleteById(dto.id());

        return true;
    }

    public List<Post> searchPosts(PostRequestDto.Search dto) {
        return postRepository.findAllByTitleContaining(dto.keyword());
    }

}

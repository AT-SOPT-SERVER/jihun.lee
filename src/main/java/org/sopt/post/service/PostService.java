package org.sopt.post.service;

import java.time.LocalDateTime;
import java.util.List;
import org.sopt.global.utils.PostValidator;
import org.sopt.post.domain.Post;
import org.sopt.post.dto.PostRequestDto;
import org.sopt.post.exception.PostNotFoundException;
import org.sopt.post.repository.PostRepository;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository){
        this.postRepository = postRepository;
    }

    public Post createPost(PostRequestDto.Create dto) {
        PostValidator.validateTitle(dto.title(), postRepository);
        LocalDateTime latestModifiedAt = postRepository.findLatestModifiedAt().orElse(null);
        PostValidator.validateCreationInterval(latestModifiedAt);

        Post post = new Post(dto.title());

        return postRepository.save(post);
    }

    public Post updatePostTitle(PostRequestDto.Update dto) {
        Post post = postRepository.findById(dto.id())
                .orElseThrow(PostNotFoundException::new);

        PostValidator.validateTitle(dto.newTitle(), postRepository);
        post.updateTitle(dto.newTitle());

        return postRepository.save(post);
    }

    public List<Post> getAllPosts() {

        return postRepository.findAll();
    }

    public Post getPostById(Long id) {

        return postRepository.findById(id)
                .orElseThrow(PostNotFoundException::new);
    }


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

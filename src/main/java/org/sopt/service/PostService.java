package org.sopt.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.sopt.domain.Post;
import org.sopt.dto.PostRequestDto;
import org.sopt.repository.PostRepository;
import org.sopt.utils.IdGenrator;
import org.sopt.validator.PostValidator;

public class PostService {

    private LocalDateTime updatedAt;
    private final PostRepository postRepository = new PostRepository();

    public void createPost(String title) {
        PostValidator.validateTitle(title, postRepository);
        PostValidator.validateCreationInterval(updatedAt);

        PostRequestDto.Create dto = new PostRequestDto.Create(title);
        Post post = new Post(IdGenrator.generateId(), dto.title());

        updatedAt = LocalDateTime.now();
        postRepository.save(post);
    }

    public boolean updatePostTitle(PostRequestDto.Update dto) {
        Optional<Post> optionalPost = postRepository.findById(dto.id());

        PostValidator.validateTitle(dto.newTitle(), postRepository);

        Post post = optionalPost.get();
        post.updateTitle(dto.newTitle());
        return true;
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post getPostById(int id) {
        return postRepository.findById(id)
                .orElse(null);
    }

    public boolean deletePostById(PostRequestDto.Delete dto) {
        return postRepository.deleteById(dto.id());
    }

    public List<Post> getAllPostByKeyword(PostRequestDto.Search dto) {
        return postRepository.findAllByKeyword(dto.keyword());
    }

    public void savePostsToFile() throws IOException {
        postRepository.savePostsToFile();
    }

    public void loadPostsFromFile() throws IOException {
        postRepository.loadPostsFromFile();
    }

}

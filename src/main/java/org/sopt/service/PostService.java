package org.sopt.service;

import static org.sopt.exception.ErrorMessage.DUPLICATED_TITLE;
import static org.sopt.exception.ErrorMessage.INVALID_TITLE_LENGTH;
import static org.sopt.exception.ErrorMessage.NOT_EMPTY_TITLE;
import static org.sopt.exception.ErrorMessage.POST_CREATION_INTERVAL_EXCEEDED;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.sopt.domain.Post;
import org.sopt.dto.PostRequestDto;
import org.sopt.repository.PostRepository;
import org.sopt.utils.IdGenrator;

public class PostService {

    private LocalDateTime updatedAt;
    private final PostRepository postRepository = new PostRepository();

    public void createPost(String title) {
        validateTitle(title);
        validateCreationInterval();

        PostRequestDto.Create dto = new PostRequestDto.Create(title);
        Post post = new Post(IdGenrator.generateId(), dto.title());

        updatedAt = LocalDateTime.now();
        postRepository.save(post);
    }

    public boolean updatePostTitle(PostRequestDto.Update dto) {
        Optional<Post> optionalPost = postRepository.findById(dto.id());

        validateTitle(dto.newTitle());

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

    private void validateTitle(String title) {
        if(title.isEmpty()){
            throw new IllegalArgumentException(NOT_EMPTY_TITLE.getMessage());
        }
        if(title.codePointCount(0, title.length()) > 30){
            throw new IllegalArgumentException(INVALID_TITLE_LENGTH.getMessage());
        }
        if(postRepository.isExistByTitle(title)){
            throw new IllegalArgumentException(DUPLICATED_TITLE.getMessage());
        }
    }

    private void validateCreationInterval() {
        if(updatedAt != null && Duration.between(updatedAt, LocalDateTime.now()).toMinutes() < 3){
            throw new IllegalStateException(POST_CREATION_INTERVAL_EXCEEDED.getMessage());
        }
    }

}

package org.sopt.service;

import static org.sopt.exception.ErrorMessage.DUPLICATED_TITLE;
import static org.sopt.exception.ErrorMessage.INVALID_TITLE_LENGTH;
import static org.sopt.exception.ErrorMessage.NOT_EMPTY_TITLE;

import java.util.List;
import java.util.Optional;
import org.sopt.domain.Post;
import org.sopt.dto.PostRequestDto;
import org.sopt.repository.PostRepository;
import org.sopt.utils.IdGenrator;

public class PostService {

    private final PostRepository postRepository = new PostRepository();

    public void createPost(String title) {
        validateTitle(title);

        PostRequestDto.Create dto = new PostRequestDto.Create(title);
        Post post = new Post(IdGenrator.generateId(), dto.title());
        postRepository.save(post);
    }

    public boolean updatePostTitle(final int id, final PostRequestDto.Update dto) {
        Optional<Post> optionalPost = postRepository.findById(id);

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

    public boolean deletePostById(int id) {
        return postRepository.deleteById(id);
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

    public List<Post> getAllPostByKeyword(String keyword) {
        return postRepository.findAllByKeyword(keyword);
    }

}

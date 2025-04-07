package org.sopt.service;

import java.util.List;
import org.sopt.domain.Post;
import org.sopt.dto.PostRequestDto;
import org.sopt.repository.PostRepository;

public class PostService {
    private final PostRepository postRepository = new PostRepository();

    public void createPost(String title) {
        PostRequestDto.Create dto = new PostRequestDto.Create(title);
        int id = postRepository.nextId();

        Post post = Post.create(dto, id);
        postRepository.save(post);
    }

    public boolean updatePostTitle(final int id, final PostRequestDto.Update dto) {
        Post post = postRepository.findById(id).get();
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
}

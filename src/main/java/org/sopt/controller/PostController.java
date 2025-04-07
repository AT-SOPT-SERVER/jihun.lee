package org.sopt.controller;

import java.util.List;
import org.sopt.domain.Post;
import org.sopt.dto.PostRequestDto;
import org.sopt.service.PostService;

public class PostController {

    private final PostService postService = new PostService();

    public boolean createPost(final String title) {
        try {
            postService.createPost(title);
            return true;

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    public Post getPostById(int id) {
        return postService.getPostById(id);
    }

    public boolean updatePostTitle(final int id, final String newTitle) {
        try {
            PostRequestDto.Update dto = new PostRequestDto.Update(newTitle);
            return postService.updatePostTitle(id, dto);

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean deletePostById(int id) {
        return postService.deletePostById(id);
    }
}

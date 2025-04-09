package org.sopt.controller;

import java.io.IOException;
import java.util.List;
import org.sopt.domain.Post;
import org.sopt.dto.PostRequestDto;
import org.sopt.service.PostService;

public class PostController {

    public static final String FILE_SAVE_ERROR = "파일 저장 중 오류 발생";
    public static final String FILE_LOAD_ERROR = "파일 불러오기 중 오류 발생";

    private final PostService postService = new PostService();

    public boolean createPost(final String title) {
        try {
            postService.createPost(title);

            return true;
        } catch (IllegalArgumentException | IllegalStateException e) {
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
            PostRequestDto.Update dto = new PostRequestDto.Update(id, newTitle);

            return postService.updatePostTitle(dto);

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());

            return false;
        }
    }

    public boolean deletePostById(int id) {
        PostRequestDto.Delete dto = new PostRequestDto.Delete(id);

        return postService.deletePostById(dto);
    }

    public List<Post> searchPostsByKeyword(String keyword) {
        PostRequestDto.Search dto = new PostRequestDto.Search(keyword);

        return postService.getAllPostByKeyword(dto);
    }

    public boolean savePostsToFile() {
        try {
            postService.savePostsToFile();

            return true;
        } catch (IOException e) {
            System.out.println(FILE_SAVE_ERROR + ": " + e.getMessage());

            return false;
        }
    }

    public boolean loadPostsFromFile() {
        try {
            postService.loadPostsFromFile();

            return true;
        } catch (IOException e) {
            System.out.println(FILE_LOAD_ERROR + ": " + e.getMessage());

            return false;
        }
    }

}

package org.sopt.post.controller;

import java.util.List;
import org.sopt.global.common.response.ApiResponse;
import org.sopt.post.domain.Post;
import org.sopt.post.dto.PostRequestDto;
import org.sopt.post.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ApiResponse<Post> createPost(@RequestBody PostRequestDto.Create dto){
        Post savedPost = postService.createPost(dto);

        return ApiResponse.response(HttpStatus.CREATED, ResponseMessage.POST_CREATE_SUCCESS.getMessage(), savedPost);
    }

    @GetMapping
    public ApiResponse<List<Post>> getAllPosts() {
        List<Post> list = postService.getAllPosts();

        return ApiResponse.response(HttpStatus.OK, ResponseMessage.POST_GET_ALL_SUCCESS.getMessage(), list);
    }

    @PutMapping("/{id}")
    public ApiResponse<Post> updatePostTitle(@PathVariable Long id, @RequestBody PostRequestDto.Update dto) {
        Post updatedPost = postService.updatePostTitle(new PostRequestDto.Update(id, dto.newTitle()));

        return ApiResponse.response(HttpStatus.OK, ResponseMessage.POST_UPDATE_SUCCESS.getMessage(), updatedPost);
    }

    @GetMapping("/{id}")
    public ApiResponse<Post> getPostById(@PathVariable Long id) {
        Post post = postService.getPostById(id);

        return ApiResponse.response(HttpStatus.OK, ResponseMessage.POST_GET_DETAIL_SUCCESS.getMessage(), post);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deletePostById(@PathVariable Long id) {
        postService.deletePostById(new PostRequestDto.Delete(id));

        return ApiResponse.response(HttpStatus.OK, ResponseMessage.POST_DELETE_SUCCESS.getMessage());
    }

    @GetMapping("/search")
    public ApiResponse<List<Post>> searchPosts(@RequestParam String keyword) {
        List<Post> result = postService.searchPosts(new PostRequestDto.Search(keyword));

        return ApiResponse.response(HttpStatus.OK, ResponseMessage.POST_SEARCH_SUCCESS.getMessage(), result);
    }

}

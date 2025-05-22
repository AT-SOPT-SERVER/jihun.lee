package org.sopt.post.controller;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.sopt.global.common.response.ApiResponse;
import org.sopt.post.domain.Post;
import org.sopt.post.dto.request.PostCreateRequest;
import org.sopt.post.dto.request.PostDeleteRequest;
import org.sopt.post.dto.request.PostSearchRequest;
import org.sopt.post.dto.request.PostUpdateRequest;
import org.sopt.post.dto.response.PostDetailResponse;
import org.sopt.post.dto.response.PostPageResponse;
import org.sopt.post.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<ApiResponse<Post>> createPost(@RequestHeader final Long userId, @Valid @RequestBody PostCreateRequest.Create dto){
        postService.createPost(dto, userId);

        return ApiResponse.response(HttpStatus.CREATED, ResponseMessage.POST_CREATE_SUCCESS.getMessage());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PostPageResponse>> getAllPosts(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        PostPageResponse postPageResponse = postService.getAllPosts(page, size);

        return ApiResponse.response(HttpStatus.OK, ResponseMessage.POST_GET_ALL_SUCCESS.getMessage(), postPageResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PostDetailResponse.Detail>> getPostById(@PathVariable final Long id) {

        return ApiResponse.response(HttpStatus.OK, ResponseMessage.POST_GET_DETAIL_SUCCESS.getMessage(), postService.getPostById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<Post>>> searchPosts(@RequestParam(required = false) final String keyword, @RequestParam(required = false) final List<String> tags) {

        return ApiResponse.response(HttpStatus.OK, ResponseMessage.POST_SEARCH_SUCCESS.getMessage(), postService.searchPosts(PostSearchRequest.Search.of(keyword, tags)));
    }

    @PatchMapping("/{id}")
        public ResponseEntity<ApiResponse<Post>> updatePost(@RequestHeader final Long userId, @PathVariable final Long id, @Valid @RequestBody PostUpdateRequest.Update dto) {
        postService.updatePost(userId, id, dto);

        return ApiResponse.response(HttpStatus.OK, ResponseMessage.POST_UPDATE_SUCCESS.getMessage());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePostById(@RequestHeader final Long userId, @PathVariable final Long id) {
        postService.deletePostById(new PostDeleteRequest.Delete(userId, id));

        return ApiResponse.response(HttpStatus.OK, ResponseMessage.POST_DELETE_SUCCESS.getMessage());
    }
}

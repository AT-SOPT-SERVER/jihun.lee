package org.sopt.like.controller;

import lombok.RequiredArgsConstructor;
import org.sopt.global.common.response.ApiResponse;
import org.sopt.like.dto.response.LikeCountResponse;
import org.sopt.like.dto.response.LikersPageResponse;
import org.sopt.like.service.PostLikeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts/{postId}/likes")
@RequiredArgsConstructor
public class PostLikeController {
    private final PostLikeService postLikeService;

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> toggleLike(@PathVariable Long postId, @RequestHeader Long userId) {
        postLikeService.togglePostLike(postId, userId);
        return ApiResponse.response(HttpStatus.OK, ResponseMessage.LIKE_TOGGLE_SUCCESS.getMessage());
    }

    @GetMapping("/count")
    public ResponseEntity<ApiResponse<LikeCountResponse>> getLikeCount(@PathVariable Long postId) {
        return ApiResponse.response(HttpStatus.OK, ResponseMessage.LIKE_COUNT_SUCCESS.getMessage(), LikeCountResponse.of(postLikeService.getPostLikeCount(postId)));
    }

    @GetMapping("/users")
    public ResponseEntity<ApiResponse<LikersPageResponse>> getLikers(@PathVariable Long postId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.response(HttpStatus.OK, ResponseMessage.LIKE_GET_USERS_SUCCESS.getMessage(), postLikeService.getPostLikers(postId, page, size));
    }

}

package org.sopt.like.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "게시글 좋아요")
@RestController
@RequestMapping("/api/likes/posts/{postId}")
@RequiredArgsConstructor
public class PostLikeController {
    private final PostLikeService postLikeService;

    @Operation(summary = "게시글 좋아요/취소")
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> toggleLike(@PathVariable final Long postId, @RequestHeader final Long userId) {
        postLikeService.togglePostLike(postId, userId);

        return ApiResponse.response(HttpStatus.OK, ResponseMessage.LIKE_TOGGLE_SUCCESS.getMessage());
    }

    @Operation(summary = "게시글 좋아요 개수 조회")
    @GetMapping("/count")
    public ResponseEntity<ApiResponse<LikeCountResponse>> getLikeCount(@PathVariable final Long postId) {

        return ApiResponse.response(HttpStatus.OK, ResponseMessage.LIKE_COUNT_SUCCESS.getMessage(), LikeCountResponse.of(postLikeService.getPostLikeCount(postId)));
    }

    @Operation(summary = "게시글 좋아요 누른 사용자 조회")
    @GetMapping("/users")
    public ResponseEntity<ApiResponse<LikersPageResponse>> getLikers(@PathVariable final Long postId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {

        return ApiResponse.response(HttpStatus.OK, ResponseMessage.LIKE_GET_USERS_SUCCESS.getMessage(), postLikeService.getPostLikers(postId, page, size));
    }
}

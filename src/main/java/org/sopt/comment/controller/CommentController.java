package org.sopt.comment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sopt.comment.dto.request.CommentCreateRequest;
import org.sopt.comment.dto.request.CommentUpdateRequest;
import org.sopt.comment.dto.response.CommentResponse;
import org.sopt.comment.dto.response.CommentResponse.Detail;
import org.sopt.comment.service.CommentService;
import org.sopt.global.common.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comments/{postId}")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<ApiResponse<Detail>> create(@PathVariable Long postId, @Valid @RequestBody CommentCreateRequest.Create dto) {
        commentService.createComment(postId, dto);
        return ApiResponse.response(HttpStatus.CREATED, ResponseMessage.COMMENT_CREATE_SUCCESS.getMessage());
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<ApiResponse<CommentResponse.Detail>> update(@PathVariable Long postId, @PathVariable Long commentId, @Valid @RequestBody CommentUpdateRequest.Update dto) {
        commentService.updateComment(postId, commentId, dto);
        return ApiResponse.response(HttpStatus.OK, ResponseMessage.COMMENT_UPDATE_SUCCESS.getMessage());
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long postId, @PathVariable Long commentId) {
        commentService.deleteComment(postId, commentId);
        return ApiResponse.response(HttpStatus.OK, ResponseMessage.COMMENT_DELETE_SUCCESS.getMessage());
    }
}

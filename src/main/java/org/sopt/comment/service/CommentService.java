package org.sopt.comment.service;

import lombok.RequiredArgsConstructor;
import org.sopt.comment.domain.Comment;
import org.sopt.comment.dto.request.CommentCreateRequest;
import org.sopt.comment.dto.request.CommentUpdateRequest;
import org.sopt.comment.exception.CommentNotFoundException;
import org.sopt.comment.repository.CommentRepository;
import org.sopt.post.domain.Post;
import org.sopt.post.exception.PostNotFoundException;
import org.sopt.post.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Transactional
    public void createComment(Long postId, CommentCreateRequest.Create dto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);
        Comment comment = new Comment(dto.content(), post);

        commentRepository.save(comment);
    }

    @Transactional
    public void updateComment(Long postId, Long commentId, CommentUpdateRequest.Update dto) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(CommentNotFoundException::new);
        if (!comment.getPost().getId().equals(postId)) {
            throw new CommentNotFoundException();
        }
        comment.updateContent(dto.content());
    }

    @Transactional
    public void deleteComment(Long postId, Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(CommentNotFoundException::new);
        if (!comment.getPost().getId().equals(postId)) {
            throw new CommentNotFoundException();
        }
        commentRepository.delete(comment);
    }
}

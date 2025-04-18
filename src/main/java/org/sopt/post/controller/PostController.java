package org.sopt.post.controller;

import java.util.List;
import org.sopt.post.domain.Post;
import org.sopt.post.dto.PostRequestDto;
import org.sopt.post.service.PostService;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Post> createPost(@RequestBody PostRequestDto.Create dto){
        Post savedPost = postService.createPost(dto);

        return ResponseEntity.ok(savedPost);
    }

    @GetMapping
    public ResponseEntity<?> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePostTitle(@PathVariable Long id, @RequestBody PostRequestDto.Update dto) {
        Post updatedPost = postService.updatePostTitle(new PostRequestDto.Update(id, dto.newTitle()));

        return ResponseEntity.ok(updatedPost);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        Post post = postService.getPostById(id);

        return ResponseEntity.ok(post);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deletePostById(@PathVariable Long id) {
        boolean result = postService.deletePostById(new PostRequestDto.Delete(id));

        return ResponseEntity.ok(result);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Post>> searchPosts(@RequestParam String keyword) {
        List<Post> posts = postService.searchPosts(new PostRequestDto.Search(keyword));

        return ResponseEntity.ok(posts);
    }

}

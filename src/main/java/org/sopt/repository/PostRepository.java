package org.sopt.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.sopt.domain.Post;

public class PostRepository {
    private int sequence = 0;

    List<Post> postList = new ArrayList<>();

    public void save(Post post) {
        postList.add(post);
    }

    public int nextId() {
        return ++sequence;
    }

    public List<Post> findAll() {
        return postList;
    }

    public Optional<Post> findById(int id) {
        return postList.stream()
                .filter(post -> post.getId() == id)
                .findFirst();
    }

    public boolean deleteById(int id) {
        return postList.removeIf(post -> post.getId() == id);
    }
}

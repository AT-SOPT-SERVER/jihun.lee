package org.sopt.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.sopt.domain.Post;

public class PostRepository {

    List<Post> postList = new ArrayList<>();

    public void save(Post post) {
        postList.add(post);
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

    public boolean isExistByTitle(String title) {
        for (Post post : postList) {
            if (post.getTitle().equals(title)) {
                return true;
            }
        }
        return false;
    }

    public List<Post> findAllByKeyword(String keyword) {
        List<Post> result = new ArrayList<>();
        for (Post post : postList) {
            if(post.getTitle().contains(keyword)){
                result.add(post);
            }
        }
        return result;
    }

}

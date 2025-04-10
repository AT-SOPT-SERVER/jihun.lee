package org.sopt.repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.sopt.domain.Post;
import static org.sopt.utils.IdGenrator.generateId;

public class PostRepository {

    private final Map<Long, Post> postMap = new HashMap<>();

    public void save(Post post) {
        postMap.put(post.getId(), post);
    }

    public List<Post> findAll() {
        return new ArrayList<>(postMap.values());
    }

    public Optional<Post> findById(int id) {
        return Optional.ofNullable(postMap.get(id));
    }

    public boolean deleteById(int id) {
        return postMap.remove(id) != null;
    }

    public boolean isExistByTitle(String title) {
        return postMap.values().stream().
                anyMatch(post -> post.getTitle().equals(title));
    }

    public List<Post> findAllByKeyword(String keyword) {
        return postMap.values().stream()
                .filter(post -> post.getTitle().contains(keyword))
                .toList();
    }

    public void savePostsToFile() throws IOException {

        String filePath = "org/sopt/assets/Post.txt";
        Files.createDirectories(Paths.get("org/sopt/assets"));

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Post post : postMap.values()) {
                writer.write(post.getId() + "|" + post.getTitle());
                writer.newLine();
            }
        }
    }

    public void loadPostsFromFile() throws IOException {

        String filePath = "org/sopt/assets/Post.txt";
        File file = new File(filePath);

        if (!file.exists()) {
            return;
        }

        postMap.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|", 2);
                if(parts.length < 2) continue;

                String title = parts[1];
                Post post = new Post(generateId(), title);
                postMap.put(post.getId(), post);
            }
        }
    }

}

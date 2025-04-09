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

    public void savePostsToFile() throws IOException {

        String filePath = "org/sopt/assets/Post.txt";
        Files.createDirectories(Paths.get("org/sopt/assets"));

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Post post : postList) {

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

        postList.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|", 2);

                if(parts.length < 2) continue;
                int id = Integer.parseInt(parts[0]);
                String title = parts[1];

                Post post = new Post(id, title);
                postList.add(post);
            }
        }
    }

}

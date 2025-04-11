package org.sopt.repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import org.sopt.domain.Post;

public class FileRepository {

    private static final String FILE_PATH = "org/sopt/assets/Post.txt";

    public void saveToFile(Collection<Post> posts) throws IOException {
        Files.createDirectories(Paths.get("org/sopt/assets"));

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Post post : posts) {
                writer.write(post.getId() + "|" + post.getTitle());
                writer.newLine();
            }
        }
    }

    public void loadFromFile(PostRepository postRepository) throws IOException {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return;
        }
        postRepository.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|", 2);

                if (parts.length < 2) continue;
                String title = parts[1];
                Post post = new Post(org.sopt.utils.IdGenrator.generateId(), title);

                postRepository.save(post);
            }
        }
    }


}

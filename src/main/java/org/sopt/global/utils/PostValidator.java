package org.sopt.global.utils;

import java.time.Duration;
import java.time.LocalDateTime;
import org.sopt.post.exception.DuplicatedTitleException;
import org.sopt.post.exception.EmptyTitleException;
import org.sopt.post.exception.InvalidTitleLengthException;
import org.sopt.post.exception.PostCreationExceededException;
import org.sopt.post.repository.PostRepository;

public class PostValidator {

    public static void validateTitle(String title, PostRepository postRepository) {
        if (title.isEmpty()) {
            throw new EmptyTitleException();
        }
        int graphemeCount = EmojiAndZwjStringUtil.countGraphemeClusters(title);
        if (graphemeCount > 30) {
            throw new InvalidTitleLengthException();
        }
        if (postRepository.existsByTitle(title)) {
            throw new DuplicatedTitleException();
        }
    }

    public static void validateCreationInterval(LocalDateTime updatedAt) {
        if (updatedAt != null && Duration.between(updatedAt, LocalDateTime.now()).toMinutes() < 3) {
            throw new PostCreationExceededException();
        }
    }

}

package org.sopt.post.service;

import java.time.Duration;
import java.time.LocalDateTime;
import org.sopt.global.utils.EmojiAndZwjStringUtil;
import org.sopt.post.exception.DuplicatedTitleException;
import org.sopt.post.exception.EmptyContentException;
import org.sopt.post.exception.EmptyTitleException;
import org.sopt.post.exception.InvalidContentLengthException;
import org.sopt.post.exception.InvalidTitleLengthException;
import org.sopt.post.exception.PostCreationExceededException;
import org.sopt.post.repository.PostRepository;

public class PostValidator {

    private static final int MAX_TITLE_LENGTH = 30;
    private static final int MAX_CONTENT_LENGTH = 1000;

    public static void validateTitle(String title, PostRepository postRepository) {
        if (title == null || title.isBlank()) {
            throw new EmptyTitleException();
        }

        int graphemeCount = EmojiAndZwjStringUtil.countGraphemeClusters(title);
        if (graphemeCount > MAX_TITLE_LENGTH) {
            throw new InvalidTitleLengthException();
        }

        if (postRepository.existsByTitle(title)) {
            throw new DuplicatedTitleException();
        }
    }

    public static void validateContent(String content) {
        if (content == null || content.isBlank()) {
            throw new EmptyContentException();
        }

        if (content.length() > MAX_CONTENT_LENGTH) {
            throw new InvalidContentLengthException();
        }

        int graphemeCount = EmojiAndZwjStringUtil.countGraphemeClusters(content);
        if (graphemeCount > MAX_CONTENT_LENGTH) {
            throw new InvalidContentLengthException();
        }
    }

    public static void validateCreationInterval(LocalDateTime updatedAt) {
        if (updatedAt != null && Duration.between(updatedAt, LocalDateTime.now()).toMinutes() < 3) {
            throw new PostCreationExceededException();
        }
    }

}

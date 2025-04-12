package org.sopt.validator;

import static org.sopt.exception.ErrorMessage.DUPLICATED_TITLE;
import static org.sopt.exception.ErrorMessage.INVALID_TITLE_LENGTH;
import static org.sopt.exception.ErrorMessage.NOT_EMPTY_TITLE;
import static org.sopt.exception.ErrorMessage.POST_CREATION_INTERVAL_EXCEEDED;
import java.time.Duration;
import java.time.LocalDateTime;
import org.sopt.repository.PostRepository;
import org.sopt.utils.PostValidatorUtil;

public class PostValidator {

    public static void validateTitle(String title, PostRepository postRepository) {
        if (title.isEmpty()) {
            throw new IllegalArgumentException(NOT_EMPTY_TITLE.getMessage());
        }
        int graphemeCount = PostValidatorUtil.countGraphemeClusters(title);
        if (graphemeCount > 30) {
            throw new IllegalArgumentException(INVALID_TITLE_LENGTH.getMessage());
        }
        if (postRepository.isExistByTitle(title)) {
            throw new IllegalArgumentException(DUPLICATED_TITLE.getMessage());
        }
    }

    public static void validateCreationInterval(LocalDateTime updatedAt) {
        if (updatedAt != null && Duration.between(updatedAt, LocalDateTime.now()).toMinutes() < 3) {
            throw new IllegalStateException(POST_CREATION_INTERVAL_EXCEEDED.getMessage());
        }
    }

}

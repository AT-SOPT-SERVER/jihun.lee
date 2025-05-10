package org.sopt.global.utils;

import java.time.Duration;
import java.time.LocalDateTime;
import org.sopt.post.exception.PostCreationExceededException;

public class PostCreationIntervalValidator {
    private static final long MIN_INTERVAL_SECONDS = 180;

    private PostCreationIntervalValidator() {

    }

    public static void validateCreationInterval(LocalDateTime lastCreatedAt) {
        if (lastCreatedAt == null) {
            return;
        }

        long elapsedSeconds = Duration.between(lastCreatedAt, LocalDateTime.now()).toSeconds();

        if (elapsedSeconds < MIN_INTERVAL_SECONDS) {
            throw new PostCreationExceededException();
        }
    }
}

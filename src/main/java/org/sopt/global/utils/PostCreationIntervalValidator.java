package org.sopt.global.utils;

import java.time.Duration;
import java.time.LocalDateTime;
import org.sopt.post.exception.PostCreationExceededException;

public class PostCreationIntervalValidator {
    public static void validateCreationInterval(LocalDateTime updatedAt) {
        if (updatedAt != null && Duration.between(updatedAt, LocalDateTime.now()).toMinutes() < 3) {
            throw new PostCreationExceededException();
        }
    }
}

package org.sopt.global.utils;

import org.sopt.comment.exception.InvalidCommentContentException;

public class CommentDtoValidator {
    private static final int MAX = 300;

    public static void validateContent(String content) {
        if (content.isBlank()) {
            throw new InvalidCommentContentException();
        }
        int count = EmojiAndZwjStringUtils.countGraphemeClusters(content);
        if (count > MAX) {
            throw new InvalidCommentContentException();
        }
    }
}

package org.sopt.global.utils;

import org.sopt.post.exception.EmptyContentException;
import org.sopt.post.exception.EmptyTitleException;
import org.sopt.post.exception.InvalidContentLengthException;
import org.sopt.post.exception.InvalidTitleLengthException;

public class PostDtoValidator {
    private static final int MAX_TITLE_LENGTH   = 30;
    private static final int MAX_CONTENT_LENGTH = 1000;

    private PostDtoValidator() {}

    public static void validateTitleStructure(String title) {
        if (title == null || title.isBlank()) {
            throw new EmptyTitleException();
        }
        int graphemeCount = EmojiAndZwjStringUtil.countGraphemeClusters(title);
        if (graphemeCount > MAX_TITLE_LENGTH) {
            throw new InvalidTitleLengthException();
        }
    }

    public static void validateContentStructure(String content) {
        if (content == null || content.isBlank()) {
            throw new EmptyContentException();
        }
        int graphemeCount = EmojiAndZwjStringUtil.countGraphemeClusters(content);
        if (graphemeCount > MAX_CONTENT_LENGTH) {
            throw new InvalidContentLengthException();
        }
    }
}

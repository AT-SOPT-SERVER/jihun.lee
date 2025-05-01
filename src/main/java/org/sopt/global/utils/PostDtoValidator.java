package org.sopt.global.utils;

import static org.sopt.global.utils.StringUtils.isNullOrBlank;

import org.sopt.post.exception.EmptyContentException;
import org.sopt.post.exception.EmptyTitleException;
import org.sopt.post.exception.InvalidContentLengthException;
import org.sopt.post.exception.InvalidTitleLengthException;

public class PostDtoValidator {
    private static final int MAX_TITLE_LENGTH   = 30;
    private static final int MAX_CONTENT_LENGTH = 1000;
    private PostDtoValidator() {}

    public static void validateTitleStructure(String title) {
        if (isNullOrBlank(title)) {
            throw new EmptyTitleException();
        }
        int graphemeCount = EmojiAndZwjStringUtils.countGraphemeClusters(title);
        if (graphemeCount > MAX_TITLE_LENGTH) {
            throw new InvalidTitleLengthException();
        }
    }

    public static void validateContentStructure(String content) {
        if (isNullOrBlank(content)) {
            throw new EmptyContentException();
        }
        int graphemeCount = EmojiAndZwjStringUtils.countGraphemeClusters(content);
        if (graphemeCount > MAX_CONTENT_LENGTH) {
            throw new InvalidContentLengthException();
        }
    }
}

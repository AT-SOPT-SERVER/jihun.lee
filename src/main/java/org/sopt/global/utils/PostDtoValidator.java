package org.sopt.global.utils;

import lombok.RequiredArgsConstructor;
import org.sopt.post.exception.InvalidContentLengthException;
import org.sopt.post.exception.InvalidTitleLengthException;

@RequiredArgsConstructor
public class PostDtoValidator {
    private static final int MAX_TITLE_LENGTH   = 30;
    private static final int MAX_CONTENT_LENGTH = 1000;

    public static void validateTitleStructure(String title) {
        int graphemeCount = EmojiAndZwjStringUtils.countGraphemeClusters(title);
        if (graphemeCount > MAX_TITLE_LENGTH) {
            throw new InvalidTitleLengthException();
        }
    }

    public static void validateContentStructure(String content) {
        int graphemeCount = EmojiAndZwjStringUtils.countGraphemeClusters(content);
        if (graphemeCount > MAX_CONTENT_LENGTH) {
            throw new InvalidContentLengthException();
        }
    }
}

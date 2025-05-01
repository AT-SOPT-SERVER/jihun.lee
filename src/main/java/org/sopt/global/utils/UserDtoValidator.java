package org.sopt.global.utils;

import static org.sopt.global.utils.StringUtils.isNullOrBlank;

import org.sopt.user.exception.EmptyNicknameException;
import org.sopt.user.exception.InvalidNicknameLengthException;

public class UserDtoValidator {
    private static final int MAX_NICKNAME_LENGTH = 10;

    public static void validateNicknameStructure(String nickname) {
        if (isNullOrBlank(nickname)) {
            throw new EmptyNicknameException();
        }

        int length = nickname.length();
        if (length > MAX_NICKNAME_LENGTH) {
            throw new InvalidNicknameLengthException();
        }
    }
}

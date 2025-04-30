package org.sopt.global.utils;

import org.sopt.user.exception.EmptyNicknameException;
import org.sopt.user.exception.InvalidNicknameLengthException;

public class UserDtoValidator {

    private static final int MAX_NICKNAME_LENGTH = 10;

    public static void validateNicknameStructure(String nickname) {
        if (nickname == null || nickname.isBlank()) {
            throw new EmptyNicknameException();
        }

        int length = nickname.length();
        if (length > MAX_NICKNAME_LENGTH) {
            throw new InvalidNicknameLengthException();
        }
    }

}

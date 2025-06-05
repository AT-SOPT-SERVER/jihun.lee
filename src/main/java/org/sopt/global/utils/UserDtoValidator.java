package org.sopt.global.utils;

import lombok.RequiredArgsConstructor;
import org.sopt.user.exception.InvalidNicknameLengthException;

@RequiredArgsConstructor
public class UserDtoValidator {
    private static final int MAX_NICKNAME_LENGTH = 10;

    public static void validateNicknameStructure(String nickname) {
        if (nickname.length() > MAX_NICKNAME_LENGTH) {
            throw new InvalidNicknameLengthException();
        }
    }
}

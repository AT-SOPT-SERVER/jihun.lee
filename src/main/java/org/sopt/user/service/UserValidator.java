package org.sopt.user.service;

import org.sopt.user.exception.DuplicatedNickNameException;
import org.sopt.user.exception.EmptyNicknameException;
import org.sopt.user.exception.InvalidNicknameLengthException;
import org.sopt.user.repository.UserRepository;

public class UserValidator {

    private static final int MAX_NICKNAME_LENGTH = 10;

    public static void validateNickname(String nickname, UserRepository userRepository) {
        if (nickname == null || nickname.isBlank()) {
            throw new EmptyNicknameException();
        }

        if (userRepository.existsByNickname(nickname)) {
            throw new DuplicatedNickNameException();
        }

        int length = nickname.length();
        if (length > MAX_NICKNAME_LENGTH) {
            throw new InvalidNicknameLengthException();
        }

    }

}

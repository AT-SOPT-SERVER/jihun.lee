package org.sopt.user.service;

import org.sopt.user.domain.User;
import org.sopt.user.dto.UserRequestDto;
import org.sopt.user.exception.DuplicatedNickNameException;
import org.sopt.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void createUser(UserRequestDto.Create dto) {
        if (userRepository.existsByNickname(dto.nickname())) {
            throw new DuplicatedNickNameException();
        }

        User user = User.of(dto.nickname());
        userRepository.save(user);
    }
}

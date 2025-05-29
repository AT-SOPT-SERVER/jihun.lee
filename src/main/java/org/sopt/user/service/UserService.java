package org.sopt.user.service;

import lombok.RequiredArgsConstructor;
import org.sopt.global.auth.jwt.dto.CreateTokenDto;
import org.sopt.global.auth.jwt.service.JwtService;
import org.sopt.user.domain.User;
import org.sopt.user.dto.request.LoginRequest;
import org.sopt.user.dto.request.RegisterRequest;
import org.sopt.user.dto.response.LoginResponse;
import org.sopt.user.exception.DuplicatedNickNameException;
import org.sopt.user.exception.UserNotFoundException;
import org.sopt.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Transactional
    public void createUser(RegisterRequest.Create dto) {
        if (userRepository.existsByNickname(dto.nickname())) {
            throw new DuplicatedNickNameException();
        }

        User user = User.of(dto.nickname());
        userRepository.save(user);
    }

    @Transactional
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByNickname(request.nickname())
                .orElseThrow(UserNotFoundException::new);

        return LoginResponse.of(user.getId(), jwtService.generateJwtToken(CreateTokenDto.from(user)));
    }
}

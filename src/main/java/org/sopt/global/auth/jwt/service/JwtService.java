package org.sopt.global.auth.jwt.service;

import lombok.RequiredArgsConstructor;
import org.sopt.global.auth.jwt.dto.CreateTokenDto;
import org.sopt.global.auth.jwt.utils.JwtExtractor;
import org.sopt.global.auth.jwt.utils.JwtProvider;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final JwtProvider jwtProvider;
    private final JwtExtractor jwtExtractor;

    public String generateJwtToken(CreateTokenDto dto) {

        return jwtProvider.generateAccessToken(dto.id(), dto.nickname());
    }
}

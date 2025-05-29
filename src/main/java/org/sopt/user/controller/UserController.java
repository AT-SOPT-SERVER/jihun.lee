package org.sopt.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sopt.global.common.response.ApiResponse;
import org.sopt.user.dto.request.LoginRequest;
import org.sopt.user.dto.request.RegisterRequest;
import org.sopt.user.dto.response.LoginResponse;
import org.sopt.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Void>> createUser(@Valid @RequestBody RegisterRequest.Create request) {
        userService.createUser(request);

        return ApiResponse.response(HttpStatus.CREATED, ResponseMessage.USER_CREATE_SUCCESS.getMessage());
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody @Valid LoginRequest request) {

        return ApiResponse.response(HttpStatus.OK, ResponseMessage.USER_LOGIN_SUCCESS.getMessage(), userService.login(request));
    }
}

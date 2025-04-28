package org.sopt.user.controller;

import org.sopt.global.common.response.ApiResponse;
import org.sopt.user.domain.User;
import org.sopt.user.dto.UserRequestDto;
import org.sopt.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public ResponseEntity<ApiResponse<User>> createUser(@RequestBody UserRequestDto.Create request) {
        User createdUser = userService.createUser(request);

        return ApiResponse.response(HttpStatus.CREATED, ResponseMessage.USER_CREATE_SUCCESS.getMessage(), createdUser);
    }

}

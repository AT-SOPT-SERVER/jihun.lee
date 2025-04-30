package org.sopt.user.controller;

import org.sopt.global.common.response.ApiResponse;
import org.sopt.user.dto.UserRequestDto;
import org.sopt.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createUser(@RequestBody UserRequestDto.Create request) {
        userService.createUser(request);

        return ApiResponse.response(HttpStatus.CREATED, ResponseMessage.USER_CREATE_SUCCESS.getMessage());
    }

}

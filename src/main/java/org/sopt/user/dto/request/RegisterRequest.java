package org.sopt.user.dto.request;

import static org.sopt.global.utils.UserDtoValidator.validateNicknameStructure;

import jakarta.validation.constraints.NotBlank;

public class RegisterRequest {

    public record Create(
            @NotBlank(message = "닉네임은 비어있을 수 없습니다.") String nickname
    ) {
       public Create {
           validateNicknameStructure(nickname);
       }
    }

}

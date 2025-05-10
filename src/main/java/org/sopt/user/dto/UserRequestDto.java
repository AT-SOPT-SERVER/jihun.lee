package org.sopt.user.dto;

import org.sopt.global.utils.UserDtoValidator;

public class UserRequestDto {

    public record Create(
            String nickname
    ) {
       public Create {
           UserDtoValidator.validateNicknameStructure(nickname);
       }
    }

}

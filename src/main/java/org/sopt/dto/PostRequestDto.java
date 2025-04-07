package org.sopt.dto;

public class PostRequestDto{

        public record Create(
                String title
        ){
        }

        public record Update(
                String newTitle
        ) {
        }
}

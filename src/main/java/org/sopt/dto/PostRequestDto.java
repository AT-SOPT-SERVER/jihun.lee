package org.sopt.dto;

public class PostRequestDto{

        public record Create(
                String title
        ){
        }

        public record Update(
                Long id,
                String newTitle
        ) {
        }

        public record Delete(
                Long id
        ) {
        }

        public record Search(
                String keyword
        ) {
        }

}

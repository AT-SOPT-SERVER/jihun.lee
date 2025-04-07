package org.sopt.dto;

public class PostRequestDto{

        public record Create(
                String title
        ){
        }

        public record Update(
                int id,
                String newTitle
        ) {
        }

        public record Delete(
                int id
        ) {
        }

        public record Search(
                String keyword
        ) {
        }

}

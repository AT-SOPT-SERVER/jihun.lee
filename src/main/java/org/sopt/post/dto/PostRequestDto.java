package org.sopt.post.dto;

public class PostRequestDto{

        public record Create(
                String title,
                String content
        ){
        }

        public record Update(
                String newTitle,
                String newContent
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

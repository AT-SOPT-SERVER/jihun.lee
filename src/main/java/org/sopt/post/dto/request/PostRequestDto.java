package org.sopt.post.dto.request;

public class PostRequestDto{

        public record Create(
                String title,
                String content,
                String tag
        ){
        }

        public record Update(
                String newTitle,
                String newContent,
                String newTag
        ) {
        }

        public record Delete(
                Long id
        ) {
        }

        public record Search(
                String keyword,
                String tag
        ) {
            public static PostRequestDto.Search of(String keyword, String tag) {
                    return new PostRequestDto.Search(keyword, tag);
            }
        }

}

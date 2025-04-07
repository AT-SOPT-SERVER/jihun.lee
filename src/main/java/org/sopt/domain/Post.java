package org.sopt.domain;

import org.sopt.dto.PostCreateRequestDto;

public class Post {
    private final int id;

    private String title;

    public Post(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public static Post create(PostCreateRequestDto dto, int id) {
        return new Post(id, dto.title());
    }

    public  int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}

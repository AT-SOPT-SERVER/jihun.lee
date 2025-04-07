package org.sopt.domain;

import org.sopt.dto.PostRequestDto;

public class Post {

    private final int id;
    private String title;

    public Post(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public static Post create(PostRequestDto.Create dto, int id) {
        return new Post(id, dto.title());
    }

    public void updateTitle(String newTitle) {
        this.title = newTitle;
    }

    public  int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}

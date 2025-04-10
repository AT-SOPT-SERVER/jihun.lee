package org.sopt.domain;

public class Post {

    private final Long id;
    private String title;

    public Post(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public void updateTitle(String newTitle) {
        this.title = newTitle;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

}

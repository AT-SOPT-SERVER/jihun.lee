package org.sopt.post.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.sopt.global.common.entity.BaseEntity;

@Entity
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    public Post() { }

    public Post(String title) {
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

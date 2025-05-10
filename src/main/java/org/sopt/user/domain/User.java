package org.sopt.user.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import org.sopt.global.common.entity.BaseEntity;
import org.sopt.post.domain.Post;

@Entity
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "author")
    @JsonManagedReference
    private List<Post> posts = new ArrayList<>();

    private String nickname;

    protected User() {

    }

    private User(String nickname) {
        this.nickname = nickname;
    }

    public static User of(String nickname) {
        return new User(nickname);
    }

    public Long getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }
}

package org.sopt.user.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sopt.global.common.entity.BaseEntity;
import org.sopt.post.domain.Post;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "author")
    @JsonManagedReference
    private List<Post> posts = new ArrayList<>();

    private String nickname;

    private User(String nickname) {
        this.nickname = nickname;
    }

    public static User of(String nickname) {
        return new User(nickname);
    }
}

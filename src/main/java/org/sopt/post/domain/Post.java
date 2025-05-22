package org.sopt.post.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.sopt.comment.domain.Comment;
import org.sopt.global.common.entity.BaseEntity;
import org.sopt.post.domain.enums.Tags;
import org.sopt.user.domain.User;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User author;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    @BatchSize(size = 50)
    @JsonManagedReference
    private List<Comment> comments = new ArrayList<>();

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "post_tags", joinColumns = @JoinColumn(name = "post_id"))
    @Column(name = "tag")
    @Enumerated(EnumType.STRING)
    @BatchSize(size = 50)
    private List<Tags> tags = new ArrayList<>();

    private String title;

    @Lob
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    public Post(String title,
                String content,
                List<Tags> tags,
                User author) {
        this.title = title;
        this.content = content;
        this.tags = new ArrayList<>(tags);
        this.author = author;
    }

    public void updatePost(String newTitle, String newContent, List<Tags> newTags) {
        this.title = newTitle;
        this.content = newContent;
        this.tags    = new ArrayList<>(newTags);
    }
}

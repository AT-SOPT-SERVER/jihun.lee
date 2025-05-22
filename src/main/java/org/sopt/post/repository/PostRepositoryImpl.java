package org.sopt.post.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.sopt.post.domain.Post;
import org.sopt.post.domain.QPost;
import org.sopt.post.domain.enums.Tags;
import org.sopt.user.domain.QUser;
import org.springframework.util.CollectionUtils;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {
    private final JPAQueryFactory query;

    @Override
    public List<Post> search(String keyword, List<Tags> tags) {
        QPost p = QPost.post;
        QUser a = QUser.user;

        BooleanExpression byKeyword = keyword == null
                ? null
                : p.title.containsIgnoreCase(keyword).or(a.nickname.containsIgnoreCase(keyword));
        BooleanExpression byTags = CollectionUtils.isEmpty(tags)
                ? null
                : p.tags.any().in(tags);

        return query
                .selectFrom(p)
                .join(p.author, a).fetchJoin()
                .where(byKeyword, byTags)
                .orderBy(p.modifiedAt.desc())
                .fetch();
    }
}

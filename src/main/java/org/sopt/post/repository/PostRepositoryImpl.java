package org.sopt.post.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.EnumPath;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.sopt.post.domain.Post;
import org.sopt.post.domain.QPost;
import org.sopt.post.domain.enums.Tags;
import org.sopt.user.domain.QUser;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {
    private final JPAQueryFactory query;

    @Override
    public List<Post> search(String keyword, List<Tags> tags) {
        QPost p = QPost.post;
        QUser a = QUser.user;

        EnumPath<Tags> tagAlias = Expressions.enumPath(Tags.class, "tag");

        BooleanExpression byKeyword = StringUtils.hasText(keyword)
                ? p.title.containsIgnoreCase(keyword)
                .or(a.nickname.containsIgnoreCase(keyword))
                : null;

        BooleanExpression byTagIn = CollectionUtils.isEmpty(tags)
                ? null
                : tagAlias.in(tags);

        List<Long> matchedIds = query
                .select(p.id)
                .from(p)
                .join(p.tags, tagAlias)
                .join(p.author, a)
                .where(byKeyword, byTagIn)
                .groupBy(p.id)
                .having(tagAlias.countDistinct().eq((long) tags.size()))
                .fetch();

        if (matchedIds.isEmpty()) {
            return List.of();
        }

        return query
                .selectFrom(p)
                .distinct()
                .join(p.author, a).fetchJoin()
                .leftJoin(p.tags, tagAlias).fetchJoin()
                .where(p.id.in(matchedIds))
                .orderBy(p.modifiedAt.desc())
                .fetch();
    }
}

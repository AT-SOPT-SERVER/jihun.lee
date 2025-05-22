package org.sopt.post.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.EnumPath;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.sopt.post.domain.Post;
import org.sopt.post.domain.QPost;
import org.sopt.post.domain.enums.Tags;
import org.sopt.user.domain.QUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {
    private final JPAQueryFactory query;

    @Override
    public Page<Post> search(String keyword, List<Tags> tags, Pageable pageable) {
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

        List<Post> content = query
                .selectFrom(p)
                .distinct()
                .join(p.author, a).fetchJoin()
                .leftJoin(p.tags, tagAlias).fetchJoin()
                .where(byKeyword, byTagIn)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(p.createdAt.desc())
                .fetch();

        Long totalCount = query
                .select(p.countDistinct())
                .from(p)
                .join(p.author, a)
                .leftJoin(p.tags, tagAlias)
                .where(byKeyword, byTagIn)
                .fetchOne();
        long total = Optional.ofNullable(totalCount).orElse(0L);

        return new PageImpl<>(content, pageable, total);
    }
}

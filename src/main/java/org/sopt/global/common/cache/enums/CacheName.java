package org.sopt.global.common.cache.enums;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.sopt.global.common.cache.exception.CacheNotFoundException;

@Getter
@AllArgsConstructor
public enum CacheName {
    POSTS_PAGE("posts_page",   Duration.ofMinutes(5),  CacheType.COMPOSITE),
    POST_DETAIL("post_detail", Duration.ofMinutes(1),  CacheType.GLOBAL),

    POST_LIKES_COUNT("post_likes_count",   Duration.ofMinutes(1),  CacheType.GLOBAL),
    POST_LIKES_USERS("post_likes_users",   Duration.ofMinutes(1),  CacheType.GLOBAL),

    COMMENT_LIKES_COUNT("comment_likes_count", Duration.ofMinutes(1), CacheType.GLOBAL),
    COMMENT_LIKES_USERS("comment_likes_users", Duration.ofMinutes(1), CacheType.GLOBAL),

    HEALTH_SUMMARY("health_summary", Duration.ofSeconds(1), CacheType.LOCAL);

    private final String cacheName;
    private final Duration ttl;
    private final CacheType cacheType;

    public static List<CacheName> entries() {
        return Arrays.asList(values());
    }

    public static CacheName of(String name) {
        return Arrays.stream(values())
                .filter(e -> e.cacheName.equals(name))
                .findFirst()
                .orElseThrow(CacheNotFoundException::new);
    }
}

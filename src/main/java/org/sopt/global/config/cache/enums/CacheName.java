package org.sopt.global.config.cache.enums;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.sopt.global.config.cache.exception.CacheNotFoundException;

@Getter
@AllArgsConstructor
public enum CacheName {
    POSTS_PAGE("posts_page",   Duration.ofMinutes(5),  CacheType.COMPOSITE),
    POST_DETAIL("post_detail", Duration.ofMinutes(1),  CacheType.GLOBAL);

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

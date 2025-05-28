package org.sopt.global.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.sopt.global.config.cache.CacheName;
import org.sopt.global.config.cache.CacheType;
import org.sopt.global.config.cache.LocalCacheManager;
import org.springframework.cache.Cache;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LocalCacheConfig {
    private static final int MAX_WEIGHT = 10_000_000;
    private static final int MIN_WEIGHT    = 1;

    @Bean
    public LocalCacheManager localCacheManager() {
        List<Cache> caches = CacheName.entries().stream()
                .filter(g -> g.getCacheType() != CacheType.GLOBAL)
                .map(g -> {
                    Caffeine<Object, Object> builder = Caffeine.newBuilder()
                            .expireAfterWrite(g.getTtl().toSeconds(), TimeUnit.SECONDS)
                            .maximumWeight(MAX_WEIGHT)
                            .weigher((key, value) -> estimateWeight(value));

                    return (Cache) new CaffeineCache(g.getCacheName(), builder.build());
                })
                .collect(Collectors.toList());

        return new LocalCacheManager(caches);
    }

    private int estimateWeight(Object value) {
        byte[] bytes = Objects.toString(value, "")
                .getBytes(StandardCharsets.UTF_8);
        return Math.max(bytes.length, MIN_WEIGHT);
    }
}

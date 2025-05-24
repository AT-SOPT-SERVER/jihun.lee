package org.sopt.global.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.List;
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

    @Bean
    public LocalCacheManager localCacheManager() {
        List<Cache> caches = CacheName.entries().stream()
                .filter(g -> g.getCacheType() != CacheType.GLOBAL)
                .map(g -> (Cache) new CaffeineCache(
                        g.getCacheName(),
                        Caffeine.newBuilder()
                                .expireAfterWrite(g.getTtl().toSeconds(), TimeUnit.SECONDS)
                                .build()
                ))
                .collect(Collectors.toList());

        return new LocalCacheManager(caches);
    }
}

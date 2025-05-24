package org.sopt.global.config;

import java.util.stream.Collectors;
import org.sopt.global.config.cache.CacheName;
import org.sopt.global.config.cache.CacheType;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisCacheConfig {
    private final RedisConnectionFactory cf;

    public RedisCacheConfig(RedisConnectionFactory cf) {
        this.cf = cf;
    }

    @Bean
    public RedisCacheConfiguration redisCacheConfiguration() {
        return RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext.SerializationPair
                        .fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair
                        .fromSerializer(new GenericJackson2JsonRedisSerializer()))
                .disableCachingNullValues();
    }

    @Bean(name = "redisCacheManager")
    public CacheManager redisCacheManager(RedisCacheConfiguration cfg) {
        var configs = CacheName.entries().stream()
                .filter(g -> g.getCacheType() != CacheType.LOCAL)
                .collect(Collectors.toMap(
                        CacheName::getCacheName,
                        g -> cfg.entryTtl(g.getTtl())
                ));

        return RedisCacheManager.builder(RedisCacheWriter.nonLockingRedisCacheWriter(cf))
                .withInitialCacheConfigurations(configs)
                .build();
    }
}

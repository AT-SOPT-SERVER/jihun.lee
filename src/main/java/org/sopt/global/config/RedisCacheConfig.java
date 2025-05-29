package org.sopt.global.config;

import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.sopt.global.common.cache.enums.CacheName;
import org.sopt.global.common.cache.enums.CacheType;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@AllArgsConstructor
public class RedisCacheConfig {
    private final RedisConnectionFactory cf;

    @Bean
    public RedisCacheConfiguration redisCacheConfiguration() {
        GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer();

        return RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(SerializationPair.fromSerializer(serializer))
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

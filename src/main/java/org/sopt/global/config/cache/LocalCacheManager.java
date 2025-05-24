package org.sopt.global.config.cache;

import jakarta.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.lang.Nullable;

@RequiredArgsConstructor
public class LocalCacheManager implements CacheManager, UpdatableCacheManager {
    private final List<Cache> initialCaches;
    private ConcurrentMap<String, Cache> cacheMap;
    private Set<String> cacheNames;

    @PostConstruct
    public void init() {
        cacheMap = new ConcurrentHashMap<>();
        cacheMap.putAll(initialCaches.stream()
                .collect(Collectors.toMap(Cache::getName, c -> c)));
        cacheNames = cacheMap.keySet();
    }

    @Override
    @Nullable
    public Cache getCache(String name) {
        return cacheMap.get(name);
    }

    @Override
    public Collection<String> getCacheNames() {
        return cacheNames;
    }

    @Override
    public void putIfAbsent(Cache cache, Object key, Object value) {
        Cache local = cacheMap.get(cache.getName());
        if (local != null) {
            local.putIfAbsent(key, value);
        }
    }
}

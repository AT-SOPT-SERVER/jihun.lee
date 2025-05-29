package org.sopt.global.common.cache;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.lang.Nullable;

@RequiredArgsConstructor
public class LocalCacheManager implements CacheManager, UpdatableCacheManager {
    private final Map<String, Cache> cacheMap;
    private final Set<String> cacheNames;

    public LocalCacheManager(List<Cache> initialCaches) {
        Map<String, Cache> map = new ConcurrentHashMap<>();
        for (Cache cache : initialCaches) {
            map.merge(
                    cache.getName(),
                    cache,
                    (existingCache, newCache) -> existingCache
            );
        }
        this.cacheMap = map;

        this.cacheNames = Collections.unmodifiableSet(
                new LinkedHashSet<>(map.keySet())
        );
    }

    @Override
    @Nullable
    public Cache getCache(String name) {
        return cacheMap.get(name);
    }

    @Override
    public Set<String> getCacheNames() {
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

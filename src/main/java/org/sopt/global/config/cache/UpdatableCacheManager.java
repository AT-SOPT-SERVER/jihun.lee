package org.sopt.global.config.cache;

import org.springframework.cache.Cache;

public interface UpdatableCacheManager {
    void putIfAbsent(Cache cache, Object key, Object value);
}

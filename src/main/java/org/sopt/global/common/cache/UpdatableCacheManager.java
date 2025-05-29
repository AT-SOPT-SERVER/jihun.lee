package org.sopt.global.common.cache;

import org.springframework.cache.Cache;

public interface UpdatableCacheManager {
    void putIfAbsent(Cache cache, Object key, Object value);
}

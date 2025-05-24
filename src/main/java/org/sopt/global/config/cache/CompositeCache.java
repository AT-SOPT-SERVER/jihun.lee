package org.sopt.global.config.cache;

import java.util.List;
import java.util.concurrent.Callable;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.lang.Nullable;

@RequiredArgsConstructor
public class CompositeCache implements Cache {
    private final List<Cache> caches;
    private final UpdatableCacheManager updater;

    @Override public String getName() {
        return caches.get(0).getName();
    }
    @Override public Object getNativeCache() {
        return caches;
    }

    @Override @Nullable
    public ValueWrapper get(Object key) {
        for (Cache c : caches) {
            ValueWrapper vw = c.get(key);
            if (vw != null && vw.get() != null) {
                updater.putIfAbsent(c, key, vw.get());
                return vw;
            }
        }
        return null;
    }

    @Override @Nullable
    public <T> T get(Object key, Class<T> type) {
        for (Cache c : caches) {
            T v = c.get(key, type);
            if (v != null) {
                updater.putIfAbsent(c, key, v);
                return v;
            }
        }
        return null;
    }

    @Override @Nullable
    public <T> T get(Object key, Callable<T> loader) {
        for (Cache c : caches) {
            try {
                T v = c.get(key, loader);
                if (v != null) {
                    updater.putIfAbsent(c, key, v);
                    return v;
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    @Override public void put(Object key, @Nullable Object value) {
        caches.forEach(c -> c.put(key, value));
    }
    @Override public void evict(Object key) {
        caches.forEach(c -> c.evict(key));
    }
    @Override public void clear() {
        caches.forEach(Cache::clear);
    }
}

package org.sopt.global.config.cache;

import jakarta.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import lombok.RequiredArgsConstructor;
import org.sopt.global.config.cache.exception.CacheNotFoundException;
import org.springframework.cache.Cache;
import org.springframework.lang.Nullable;

@RequiredArgsConstructor
public class CompositeCache implements Cache {
    private final List<Cache> caches;
    private final UpdatableCacheManager updater;

    @PostConstruct
    private void validate() {
        if (caches == null || caches.isEmpty()) {
            throw new CacheNotFoundException();
        }
    }

    @Override
    public String getName() {
        return caches.get(0).getName();
    }
    @Override
    public Object getNativeCache() {
        return Collections.unmodifiableList(caches);
    }

    @Override
    @Nullable
    public ValueWrapper get(Object key) {
        return findAndUpdate(key, (cache) -> cache.get(key));
    }

    @Override
    @Nullable
    public <T> T get(Object key, Class<T> type) {
        return findAndUpdate(key, (cache) -> cache.get(key, type));
    }

    @Override
    @Nullable
    public <T> T get(Object key, Callable<T> loader) {
        for (Cache c : caches) {
            try {
                T value = c.get(key, loader);
                if (value != null) {
                    updater.putIfAbsent(c, key, value);
                    return value;
                }
            } catch (Exception ignore) {
            }
        }
        return null;
    }

    @Override
    public void put(Object key, @Nullable Object value) {
        caches.forEach(c -> c.put(key, value));
    }
    @Override
    public void evict(Object key) {
        caches.forEach(c -> c.evict(key));
    }
    @Override
    public void clear() {
        caches.forEach(Cache::clear);
    }

    @Nullable
    private <T> T findAndUpdate(Object key, CacheInvoker<T> invoker) {
        for (Cache cache : caches) {
            T value;
            try {
                value = invoker.invoke(cache);
            } catch (Exception ex) {
                throw new ValueRetrievalException(key, () -> null, ex);
            }
            if (value != null) {
                updater.putIfAbsent(cache, key, value);
                return value;
            }
        }
        return null;
    }

    @FunctionalInterface
    private interface CacheInvoker<T> {
        T invoke(Cache cache) throws Exception;
    }

}

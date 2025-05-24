package org.sopt.global.config.cache;

import jakarta.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

@RequiredArgsConstructor
public class CompositeCacheCacheManager implements CacheManager {
    private final List<CacheManager> managers;
    private final UpdatableCacheManager updater;
    private List<String> cacheNames;

    @PostConstruct
    public void init() {
        this.cacheNames = managers.stream()
                .flatMap(m -> m.getCacheNames().stream())
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public Cache getCache(String name) {
        CacheType type = CacheName.of(name).getCacheType();
        if (type == CacheType.COMPOSITE) {
            var found = managers.stream()
                    .map(m -> m.getCache(name))
                    .filter(c -> c != null)
                    .toList();
            return new CompositeCache(found, updater);
        }
        return managers.stream()
                .map(m -> m.getCache(name))
                .filter(c -> c != null)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Collection<String> getCacheNames() {
        return cacheNames;
    }
}

package org.sopt.global.config.cache;

import jakarta.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.sopt.global.config.cache.enums.CacheName;
import org.sopt.global.config.cache.enums.CacheType;
import org.sopt.global.config.cache.exception.CacheNotFoundException;
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
        List<Cache> found = managers.stream()
                .map(m -> m.getCache(name))
                .filter(Objects::nonNull)
                .toList();

        if (found.isEmpty()) {
            throw new CacheNotFoundException();
        }

        if (type == CacheType.COMPOSITE) {
            return new CompositeCache(found, updater);
        } else {
            return found.get(0);
        }
    }

    @Override
    public Collection<String> getCacheNames() {
        return cacheNames;
    }
}

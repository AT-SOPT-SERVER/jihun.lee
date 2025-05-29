package org.sopt.global.common.service;

import lombok.RequiredArgsConstructor;
import org.sopt.global.common.response.HealthSummary;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HealthCheckService {

    @Cacheable(cacheNames = "health_summary")
    public HealthSummary getHealthSummary() {
        Runtime rt = Runtime.getRuntime();
        long free   = rt.freeMemory();
        long total  = rt.totalMemory();
        int  threads = Thread.activeCount();
        return new HealthSummary(free, total, threads);
    }
}

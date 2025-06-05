package org.sopt.global.common.response;

public record HealthSummary(
        long freeMemory,
        long totalMemory,
        int  threadCount
) {
}

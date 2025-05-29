package org.sopt.global.common.controller;

import lombok.RequiredArgsConstructor;
import org.sopt.global.common.response.HealthSummary;
import org.sopt.global.common.service.HealthCheckService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StatusCheckController {
    private final HealthCheckService healthCheckService;

    @GetMapping("/health-check")
    public ResponseEntity<HealthSummary> checkHealthStatus() {
        return ResponseEntity.ok(healthCheckService.getHealthSummary());
    }
}

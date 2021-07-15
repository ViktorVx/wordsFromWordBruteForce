package org.pva.wfwbf.controller;

import org.pva.wfwbf.dto.HealthDto;
import org.pva.wfwbf.dto.HealthStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping("/health")
    public HealthDto healthCheck() {
        return HealthDto.builder().health(HealthStatus.OK).build();
    }
}

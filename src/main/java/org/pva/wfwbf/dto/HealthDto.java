package org.pva.wfwbf.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HealthDto {

    private HealthStatus health;
}

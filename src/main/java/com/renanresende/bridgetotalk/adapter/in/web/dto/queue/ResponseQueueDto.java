package com.renanresende.bridgetotalk.adapter.in.web.dto.queue;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.renanresende.bridgetotalk.domain.attendance.DistributionStrategy;

import java.time.Instant;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ResponseQueueDto(
        UUID id,
        String name,
        DistributionStrategy distributionStrategy,
        Instant createdAt,
        Instant updatedAt,
        Instant deletedAt
) {
}

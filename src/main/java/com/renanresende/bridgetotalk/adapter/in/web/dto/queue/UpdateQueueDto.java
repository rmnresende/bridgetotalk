package com.renanresende.bridgetotalk.adapter.in.web.dto.queue;

import com.renanresende.bridgetotalk.domain.attendance.DistributionStrategy;
import com.renanresende.bridgetotalk.domain.attendance.QueueSettings;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UpdateQueueDto(

        @NotNull
        UUID companyId,

        @NotBlank
        String name,

        @NotNull
        DistributionStrategy distributionStrategy,

        QueueSettings settings
) {
}

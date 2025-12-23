package com.renanresende.bridgetotalk.adapter.in.web.dto.queue;

import com.renanresende.bridgetotalk.domain.attendance.DistributionStrategy;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateQueueDto(

         @NotNull
         UUID companyId,

         @NotNull
         String name,

         @NotNull
         DistributionStrategy distributionStrategy,

         QueueSettingsDto settings
) {
}

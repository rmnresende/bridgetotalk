package com.renanresende.bridgetotalk.application.port.in.command;

import com.renanresende.bridgetotalk.domain.attendance.DistributionStrategy;
import com.renanresende.bridgetotalk.domain.attendance.QueueSettings;

import java.util.UUID;

public record UpdateQueueCommand (
        UUID id,
        UUID companyId,
        String name,
        DistributionStrategy distributionStrategy,
        QueueSettings settings
) {
}

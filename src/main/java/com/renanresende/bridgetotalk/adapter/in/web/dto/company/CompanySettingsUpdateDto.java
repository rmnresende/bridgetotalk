package com.renanresende.bridgetotalk.adapter.in.web.dto.company;

import com.renanresende.bridgetotalk.domain.organization.Plan;

public record CompanySettingsUpdateDto(
        Integer maxAgents,
        Integer maxQueues,
        String timezone,
        String language,
        Plan plan,
        int maxConcurrentConversationsPerAgent
) {}
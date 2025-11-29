package com.renanresende.bridgetotalk.adapter.in.web.dto;

import com.renanresende.bridgetotalk.domain.Plan;

public record CompanySettingsUpdateDto(
        Integer maxAgents,
        Integer maxQueues,
        String timezone,
        String language,
        Plan plan
) {}
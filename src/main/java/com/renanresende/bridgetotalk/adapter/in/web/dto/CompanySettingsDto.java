package com.renanresende.bridgetotalk.adapter.in.web.dto;

import com.renanresende.bridgetotalk.domain.Plan;

import java.time.Instant;

public record CompanySettingsDto(
        Integer maxAgents,
        Integer maxQueues,
        String timezone,
        String language,
        Plan plan
) {}

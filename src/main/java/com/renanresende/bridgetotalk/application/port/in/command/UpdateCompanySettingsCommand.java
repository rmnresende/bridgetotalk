package com.renanresende.bridgetotalk.application.port.in.command;

import com.renanresende.bridgetotalk.domain.organization.Plan;

public record UpdateCompanySettingsCommand(
        Integer maxAgents,
        Integer maxQueues,
        String timezone,
        String language,
        Plan plan
) {}

package com.renanresende.bridgetotalk.adapter.in.web.dto;

import com.fasterxml.jackson.databind.annotation.EnumNaming;
import com.renanresende.bridgetotalk.domain.AgentStatus;

import java.util.UUID;

public record UpdateAgentDto (
        UUID companyId,
        AgentStatus status
) {}

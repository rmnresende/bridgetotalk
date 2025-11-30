package com.renanresende.bridgetotalk.adapter.in.web.dto;

import com.renanresende.bridgetotalk.domain.AgentRole;
import com.renanresende.bridgetotalk.domain.AgentStatus;

import java.time.Instant;
import java.util.UUID;

public record AgentDto(
        UUID id,
        UUID companyId,
        String name,
        String email,
        String passwordHash,
        AgentRole role,
        AgentStatus status
) {
}

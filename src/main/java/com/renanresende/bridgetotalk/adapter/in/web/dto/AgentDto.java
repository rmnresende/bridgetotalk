package com.renanresende.bridgetotalk.adapter.in.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.renanresende.bridgetotalk.domain.AgentRole;
import com.renanresende.bridgetotalk.domain.AgentStatus;

import java.time.Instant;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record AgentDto(
        UUID id,
        UUID companyId,
        String name,
        String email,
        String passwordHash,
        AgentRole role,
        AgentStatus status,

        @JsonFormat(shape = JsonFormat.Shape.STRING)
        Instant createdAt,

        @JsonFormat(shape = JsonFormat.Shape.STRING)
        Instant deletedAt
) {
}

package com.renanresende.bridgetotalk.application.port.in.command;

import com.renanresende.bridgetotalk.domain.AgentRole;
import com.renanresende.bridgetotalk.domain.AgentStatus;

import java.util.UUID;

public record CreateAgentCommand(
        UUID id,
        UUID companyId,
        String name,
        String email,
        String passwordHash,
        AgentRole role,
        AgentStatus status
) {}
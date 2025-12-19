package com.renanresende.bridgetotalk.application.port.in.command;

import com.renanresende.bridgetotalk.domain.people.AgentRole;

import java.util.UUID;

public record CreateAgentCommand(
        UUID companyId,
        String name,
        String email,
        String passwordHash,
        AgentRole role
) {}
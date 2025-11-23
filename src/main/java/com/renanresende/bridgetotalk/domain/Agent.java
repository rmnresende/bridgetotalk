package com.renanresende.bridgetotalk.domain;

import java.time.Instant;
import java.util.UUID;

public class Agent {

    private final UUID id;
    private final UUID companyId;
    private String name;
    private String email;
    private String passwordHash;
    private AgentRole role;
    private AgentStatus status;
    private final Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;

    public Agent(UUID id, UUID companyId, Instant createdAt) {
        this.id = id;
        this.companyId = companyId;
        this.createdAt = createdAt;
    }
}
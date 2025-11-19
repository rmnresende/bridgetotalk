package com.renanresende.talkbridge.domain;

import java.time.Instant;
import java.util.UUID;

public class Agent {

    private final UUID id;
    private final UUID companyId;
    private String name;
    private String email;
    private String passwordHash;
    private AgentRole role; // Importado de com.renanresende.talkbridge.domain
    private AgentStatus status; // Importado de com.renanresende.talkbridge.domain
    private final Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;

    public Agent(UUID id, UUID companyId, Instant createdAt) {
        this.id = id;
        this.companyId = companyId;
        this.createdAt = createdAt;
    }
}
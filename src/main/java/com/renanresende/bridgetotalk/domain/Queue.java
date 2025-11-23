package com.renanresende.bridgetotalk.domain;

import java.time.Instant;
import java.util.UUID;

public class Queue {

    private final UUID id;
    private final UUID companyId;
    private String name;
    private String status;
    private DistributionStrategy distributionStrategy; // Importado de com.renanresende.talkbridge.domain
    private final Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;

    public Queue(UUID id, UUID companyId, Instant createdAt) {
        this.id = id;
        this.companyId = companyId;
        this.createdAt = createdAt;
    }
}
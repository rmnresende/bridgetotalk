package com.renanresende.bridgetotalk.domain;

import java.time.Instant;
import java.util.UUID;

public class Conversation {

    private final UUID id;
    private final UUID companyId;
    private final UUID customerId;
    private UUID agentId;
    private final UUID queueId;
    private String protocol;
    private String channelType;
    private ConversationStatus status; // Importado de com.renanresende.talkbridge.domain
    private String priority;
    private final Instant createdAt;
    private Instant updatedAt;

    public Conversation(UUID id, UUID companyId, UUID customerId, UUID queueId, Instant createdAt) {
        this.id = id;
        this.companyId = companyId;
        this.customerId = customerId;
        this.queueId = queueId;
        this.createdAt = createdAt;
    }
}
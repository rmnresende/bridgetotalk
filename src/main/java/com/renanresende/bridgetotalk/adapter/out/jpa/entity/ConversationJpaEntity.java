package com.renanresende.bridgetotalk.adapter.out.jpa.entity;

import com.renanresende.bridgetotalk.domain.attendance.ConversationStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;

import java.time.Instant;
import java.util.UUID;

public class ConversationJpaEntity {

    @Id
    private UUID id;

    @Column(name = "company_id", columnDefinition = "UUID", nullable = false)
    private UUID companyId;

    @Column(name = "customer_id", columnDefinition = "UUID", nullable = false)
    private UUID customerId;

    @Column(name = "queue_id", columnDefinition = "UUID", nullable = false)
    private UUID queueId;

    @Column(name = "channel_id", columnDefinition = "UUID", nullable = false)
    private UUID channelId;

    @Column(name = "agent_id")
    private UUID agentId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ConversationStatus status;

    private String protocol;
    private String priority;

    @Column(name = "started_at")
    private Instant startedAt;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;
}

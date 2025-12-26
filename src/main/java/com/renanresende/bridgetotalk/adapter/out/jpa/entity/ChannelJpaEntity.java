package com.renanresende.bridgetotalk.adapter.out.jpa.entity;

import com.renanresende.bridgetotalk.domain.channel.ChannelType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;

import java.time.Instant;
import java.util.UUID;

public class ChannelJpaEntity {

    @Id
    private UUID id;

    @Column(name = "company_id", nullable = false)
    private UUID companyId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ChannelType type;

    @Column(nullable = false)
    private String name;

    @Column(name = "external_identifier", nullable = false)
    private String externalIdentifier;

    @Column(nullable = false)
    private boolean active;

    @Column(name = "default_queue_id", nullable = false)
    private UUID defaultQueueId;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;
}

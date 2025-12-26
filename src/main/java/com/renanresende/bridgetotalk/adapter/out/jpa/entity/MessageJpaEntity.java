package com.renanresende.bridgetotalk.adapter.out.jpa.entity;

import com.renanresende.bridgetotalk.domain.attendance.MessageContentType;
import com.renanresende.bridgetotalk.domain.attendance.SenderType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;

import java.time.Instant;
import java.util.UUID;

public class MessageJpaEntity {

    @Id
    private UUID id;

    @Column(name = "conversation_id", nullable = false)
    private UUID conversationId;

    private UUID agentId;
    private UUID customerId;

    @Column(name = "external_id")
    private String externalId;

    @Enumerated(EnumType.STRING)
    private SenderType senderType;

    @Enumerated(EnumType.STRING)
    private MessageContentType contentType;

    @Column(columnDefinition = "text")
    private String content;

    private String status;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;
}

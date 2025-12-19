package com.renanresende.bridgetotalk.domain.attendance;

import java.time.Instant;
import java.util.UUID;

public class Message {

    private final UUID id;
    private final UUID conversationId;
    private SenderType senderType; // Importado de com.renanresende.talkbridge.domain
    private String content;
    private String contentType;
    private String status;
    private final Instant createdAt;

    public Message(UUID id, UUID conversationId, Instant createdAt) {
        this.id = id;
        this.conversationId = conversationId;
        this.createdAt = createdAt;
    }
}
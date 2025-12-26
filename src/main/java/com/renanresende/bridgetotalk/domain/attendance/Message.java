package com.renanresende.bridgetotalk.domain.attendance;

import java.time.Instant;
import java.util.UUID;

public class Message {

    private final UUID id;
    private final UUID conversationId;
    private UUID agentId;
    private UUID customerId;
    private String externalId;
    private SenderType senderType;
    private String content;
    private String status;
    private final Instant createdAt;
    private MessageContentType contentType;


    private Message(UUID id,
                    UUID conversationId,
                    SenderType senderType,
                    String content,
                    MessageContentType contentType,
                    Instant createdAt) {
        this.id = id;
        this.conversationId = conversationId;
        this.senderType = senderType;
        this.content = content;
        this.contentType = contentType;
        this.createdAt = createdAt;
    }

    public static Message customerMessage(UUID conversationId, String content) {
        return new Message(
                UUID.randomUUID(),
                conversationId,
                SenderType.CUSTOMER,
                content,
                MessageContentType.TEXT,
                Instant.now()
        );
    }

    public static Message systemNotice(UUID conversationId, String text) {
        return new Message(UUID.randomUUID(),
                           conversationId,
                           SenderType.SYSTEM,
                           text, MessageContentType.TEXT,
                           Instant.now()
        );
    }

}
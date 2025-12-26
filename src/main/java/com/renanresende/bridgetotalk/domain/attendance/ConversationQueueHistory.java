package com.renanresende.bridgetotalk.domain.attendance;

import java.time.Instant;
import java.util.UUID;

public class ConversationQueueHistory {

    private UUID id;
    private UUID conversationId;
    private UUID fromQueueId;
    private UUID toQueueId;
    private UUID transferredByAgentId; // null if automatic
    private TransferReason reason;
    private Instant transferredAt;

    private ConversationQueueHistory(UUID id,
                                     UUID conversationId,
                                     UUID fromQueueId,
                                     UUID toQueueId,
                                     UUID transferredByAgentId,
                                     TransferReason reason,
                                     Instant transferredAt) {
        this.id = id;
        this.conversationId = conversationId;
        this.fromQueueId = fromQueueId;
        this.toQueueId = toQueueId;
        this.transferredByAgentId = transferredByAgentId;
        this.reason = reason;
        this.transferredAt = transferredAt;
    }


    public static ConversationQueueHistory create(
            UUID conversationId,
            UUID fromQueueId,
            UUID toQueueId,
            UUID transferredByAgentId,
            TransferReason reason
    ) {
        return new ConversationQueueHistory(
                UUID.randomUUID(),
                conversationId,
                fromQueueId,
                toQueueId,
                transferredByAgentId,
                reason,
                Instant.now()
        );
    }

}

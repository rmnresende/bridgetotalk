package com.renanresende.bridgetotalk.adapter.out.jpa.entity;

import com.renanresende.bridgetotalk.domain.attendance.TransferReason;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "conversation_queue_history")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConversationQueueHistoryJpaEntity {

    @Id
    @Column(nullable = false, updatable = false)
    private UUID id;

    @Column(name = "conversation_id", columnDefinition = "UUID", nullable = false, updatable = false)
    private UUID conversationId;

    @Column(name = "from_queue_id", columnDefinition = "UUID", nullable = true, updatable = false )
    private UUID fromQueueId;

    @Column(name = "to_queue_id",columnDefinition = "UUID", nullable = false, updatable = false)
    private UUID toQueueId;

    @Column(name = "transferred_by_agent_id", columnDefinition = "UUID", nullable = true, updatable = false)
    private UUID transferredByAgentId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private TransferReason reason;

    @Column(name = "transferred_at", nullable = false, updatable = false)
    private Instant transferredAt;

    @Column(name = "details")
    private String details;
}

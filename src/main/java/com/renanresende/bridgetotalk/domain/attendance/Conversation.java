package com.renanresende.bridgetotalk.domain.attendance;

import com.renanresende.bridgetotalk.domain.shared.exception.BusinessException;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import static com.renanresende.bridgetotalk.domain.attendance.ConversationStatus.OUT_OF_BUSINESS_HOURS;

public class Conversation {

    private UUID id;
    private UUID companyId;
    private UUID customerId;
    private UUID agentId;
    private UUID currentQueueId;
    private String protocol;
    private String channelType;
    private ConversationStatus status;
    private String priority;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant startedAt; // Quando o agente deu o "aceitar"
    private Map<String, String> metadata = new HashMap<>(); // Info do canal (telefone, browser, etc)

    private Conversation(UUID id,
                        UUID companyId,
                        UUID customerId,
                        UUID queueId,
                        Instant createdAt,
                        ConversationStatus status) {
        this.id = id;
        this.companyId = companyId;
        this.customerId = customerId;
        this.currentQueueId = queueId;
        this.createdAt = createdAt;
        this.status = status;
    }

    public static Conversation startOutsideBusinessHours(
            UUID companyId,
            UUID customerId,
            UUID queueId
    ) {
        var now = Instant.now();
        return new Conversation(
                UUID.randomUUID(),
                companyId,
                customerId,
                queueId,
                now,
              OUT_OF_BUSINESS_HOURS
        );
    }

    public static Conversation startWaitingInQueue(
            UUID companyId,
            UUID customerId,
            UUID queueId
    ) {
        var now = Instant.now();
        return new Conversation(
                UUID.randomUUID(),
                companyId,
                customerId,
                queueId,
                now,
                ConversationStatus.WAITING_FOR_ATTENDANCE
        );
    }

    public void assignAgent(UUID agentId) {
        if (status != ConversationStatus.WAITING_FOR_ATTENDANCE) {
            throw new BusinessException("Conversation is not eligible for assignment");
        }
        this.agentId = agentId;
        this.status = ConversationStatus.IN_ATTENDANCE;
        this.startedAt = Instant.now();
        this.updatedAt = this.startedAt;
    }

    public void markWaitingForCustomer() {
        if (status != ConversationStatus.IN_ATTENDANCE) {
            throw new BusinessException("Invalid state transition");
        }
        this.status = ConversationStatus.WAITING_FOR_CUSTOMER;
        this.updatedAt = Instant.now();
    }

    public void close() {
        if (status == ConversationStatus.CLOSED) {
            throw new BusinessException("Conversation already closed");
        }
        this.status = ConversationStatus.CLOSED;
        this.updatedAt = Instant.now();
    }

    public void transferToQueue(UUID newQueueId) {
        if (Objects.equals(this.currentQueueId, newQueueId)) {
            throw new BusinessException("Conversation is already in this queue");
        }

        this.currentQueueId = newQueueId;
        this.updatedAt = Instant.now();
    }


}
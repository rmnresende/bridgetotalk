package com.renanresende.bridgetotalk.domain.people;

import com.renanresende.bridgetotalk.domain.shared.exception.BusinessException;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
public class Agent {

    private UUID id;
    private UUID companyId;
    private String name;
    private String email;
    private String passwordHash;
    private AgentRole role;
    private AgentStatus status;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;
    private int activeConversations;

    private Agent(UUID id,
                  UUID companyId,
                  String name,
                  String email,
                  String passwordHash,
                  AgentRole role,
                  AgentStatus status,
                  Instant createdAt,
                  Instant updatedAt,
                  Instant deletedAt,
                  int activeConversations
    ){
        this.id = id;
        this.companyId = companyId;
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.activeConversations = activeConversations;
    }

    public static Agent rehydrate(UUID id,
                                  UUID companyId,
                                  String name,
                                  String email,
                                  String passwordHash,
                                  AgentRole role,
                                  AgentStatus status,
                                  Instant createdAt,
                                  Instant updatedAt,
                                  Instant deletedAt,
                                  int activeConversations
    ){
       return new Agent(id,
                        companyId,
                        name,
                        email,
                        passwordHash,
                        role,
                        status,
                        createdAt,
                        updatedAt,
                        deletedAt,
                        activeConversations
       );
    }

    public static Agent createNew(
                                  UUID companyId,
                                  String name,
                                  String email,
                                  String passwordHash,
                                  AgentRole role) {

        var now = Instant.now();

        return new Agent(
                UUID.randomUUID(),// generate domain values internally
                companyId,
                name,
                email,
                passwordHash,
                role,
                AgentStatus.OFFLINE, // new agent start with offline status
                now,
                now,
                null,
                0 //new agent start with no active conversations
        );
    }

    public void changeStatus(AgentStatus newStatus) throws BusinessException {

        if (this.status == newStatus){
            throw new BusinessException("Agent already in status: " + newStatus);
        }

        this.status = newStatus;
        this.updatedAt = Instant.now();
    }

    public boolean isNotAvailable(){
        return AgentStatus.AVAILABLE != this.status;
    }

    public boolean canReceiveNewConversation(int maxConcurrent) {
        if (status == AgentStatus.OFFLINE || status == AgentStatus.PAUSED) {
            return false;
        }
        return activeConversations < maxConcurrent;
    }

    public void incrementActiveConversations() {
        this.activeConversations++;
    }

    public void decrementActiveConversations() {
        if (this.activeConversations == 0) {
            throw new BusinessException("Active conversations cannot be negative");
        }
        this.activeConversations--;
    }
}
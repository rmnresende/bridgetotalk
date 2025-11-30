package com.renanresende.bridgetotalk.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
public class Agent {

    private final UUID id;
    private final UUID companyId;
    private String name;
    private String email;
    private String passwordHash;
    private AgentRole role;
    private AgentStatus status;
    private final Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;

    public Agent(UUID id, UUID companyId, String name, String email, String passwordHash, AgentRole role, AgentStatus status) {
        this.id = id;
        this.companyId = companyId;
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
        this.status = status;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    // Método/Construtor para CRIAR uma nova entidade (usado pelo Service)
    public static Agent createNew(UUID id, UUID companyId, String name, String email, String passwordHash, AgentRole role) {
        // Gera valores de Domínio internamente
        return new Agent(
                id,
                companyId,
                name,
                email,
                passwordHash,
                role,
                AgentStatus.OFFLINE // Novo Agente deve começar como OFFLINE ou PAUSED
        );
    }

    // Método para mudar o status (com lógica de negócio se houver)
    public void changeStatus(AgentStatus newStatus) {
        this.status = newStatus;
        this.updatedAt = Instant.now();
    }
}
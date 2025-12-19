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

    private Agent(UUID id,
                  UUID companyId,
                  String name,
                  String email,
                  String passwordHash,
                  AgentRole role,
                  AgentStatus status,
                  Instant createdAt,
                  Instant updatedAt,
                  Instant deletedAt
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
                                  Instant deletedAt
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
                        deletedAt
       );
    }



    // Método/Construtor para CRIAR uma nova entidade (usado pelo Service)
    public static Agent createNew(
                                  UUID companyId,
                                  String name,
                                  String email,
                                  String passwordHash,
                                  AgentRole role) {

        var now = Instant.now(); // Gera valores de Domínio internamente

        return new Agent(
                UUID.randomUUID(),// Gera valores de Domínio internamente
                companyId,
                name,
                email,
                passwordHash,
                role,
                AgentStatus.OFFLINE, // Novo Agente deve começar como OFFLINE
                now,
                now,
                null
        );
    }

    public void changeStatus(AgentStatus newStatus) throws BusinessException {

        if (this.status == newStatus){
            throw new BusinessException("Agent already in status: " + newStatus);
        }

        this.status = newStatus;
        this.updatedAt = Instant.now();
    }
}
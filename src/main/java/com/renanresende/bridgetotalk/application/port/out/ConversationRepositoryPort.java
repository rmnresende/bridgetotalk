package com.renanresende.bridgetotalk.application.port.out;

import com.renanresende.bridgetotalk.domain.Conversation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ConversationRepositoryPort {

    Conversation save(Conversation conversation);

    Optional<Conversation> findById(UUID id);

    // Método para buscar conversas prontas para roteamento
    List<Conversation> findWaitingInQueue(UUID companyId);

    // Método para buscar conversas ativas de um agente específico
    List<Conversation> findActiveByAgentId(UUID agentId);
}
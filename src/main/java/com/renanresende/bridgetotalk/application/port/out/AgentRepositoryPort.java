package com.renanresende.bridgetotalk.application.port.out;

import com.renanresende.bridgetotalk.domain.Agent;
import java.util.Optional;
import java.util.UUID;

public interface AgentRepositoryPort {

    Agent save(Agent agent);

    Optional<Agent> findById(UUID id);

    // Essencial para login: encontrar um agente pelo email dentro de uma empresa
    Optional<Agent> findByCompanyIdAndEmail(UUID companyId, String email);

    // Contrato para o relacionamento N:M:
    void associateAgentToQueue(UUID agentId, UUID queueId);
}
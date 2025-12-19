package com.renanresende.bridgetotalk.application.port.out;

import com.renanresende.bridgetotalk.adapter.in.web.dto.agent.AgentFilter;
import com.renanresende.bridgetotalk.domain.people.Agent;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AgentRepositoryPort {

    Agent save(Agent agent);

    Optional<Agent> findById(UUID id);

    Optional<Agent> findActiveAgentByIdAndCompanyId(UUID id, UUID companyId);

    Optional<Agent> findActiveAgentByCompanyIdAndEmail(UUID companyId, String email);

    List<Agent> findAllActiveAgentsByCompanyId(AgentFilter agentFilter, UUID companyId);

    void associateAgentToQueue(UUID agentId, UUID queueId);

    void updateStatus(Agent agent);

    void deleteAgent(UUID agentId, UUID companyId);
}
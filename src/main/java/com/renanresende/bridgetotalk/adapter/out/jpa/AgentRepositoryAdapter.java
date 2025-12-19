package com.renanresende.bridgetotalk.adapter.out.jpa;

import com.renanresende.bridgetotalk.adapter.in.web.dto.agent.AgentFilter;
import com.renanresende.bridgetotalk.adapter.out.jpa.mapper.AgentJpaMapper;
import com.renanresende.bridgetotalk.adapter.out.jpa.spec.AgentSpecification;
import com.renanresende.bridgetotalk.adapter.out.jpa.spec.SortParams;
import com.renanresende.bridgetotalk.application.port.out.AgentRepositoryPort;
import com.renanresende.bridgetotalk.domain.Agent;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class AgentRepositoryAdapter implements AgentRepositoryPort {

    private final SpringDataAgentRepository repository;
    private final AgentJpaMapper mapper;

    public AgentRepositoryAdapter(SpringDataAgentRepository repository, AgentJpaMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Agent save(Agent agent) {
        var entity = mapper.toEntity(agent);
        return mapper.toDomain(repository.save(entity));
    }

    @Override
    public Optional<Agent> findById(UUID id) {
        return repository.findById(id)
                         .map(mapper::toDomain);
    }

    @Override
    public Optional<Agent> findActiveAgentByIdAndCompanyId(UUID id, UUID companyId) {
        return repository.findActiveAgentByIdAndCompanyId(id, companyId)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<Agent> findActiveAgentByCompanyIdAndEmail(UUID companyId, String email) {
        return repository.findActiveByEmailAndCompanyId(email, companyId)
                .map(mapper::toDomain);
    }

    @Override
    public List<Agent> findAllActiveAgentsByCompanyId(AgentFilter agentFilter, UUID companyId) {

        var especification = AgentSpecification.withOptionalFiltersByCompany(agentFilter, companyId);
        var sortField = SortParams.validateFieldToSort(agentFilter.queryOptions().sortBy().orElse(null), "agent");
        var sortDirection = SortParams.validateDirection(agentFilter.queryOptions().sortDirection().orElse(null));

        return repository.findAll(especification, Sort.by(sortDirection, sortField))
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void associateAgentToQueue(UUID agentId, UUID queueId) {

    }

    @Override
    @Transactional
    public void deleteAgent(UUID agentId, UUID companyId) {

        findActiveAgentByIdAndCompanyId(agentId, companyId);
        repository.deleteAgent(agentId, Instant.now());
    }

    @Override
    @Transactional
    public void updateStatus(Agent agent) {
        repository.updateStatus(agent.getId(), agent.getStatus(), agent.getUpdatedAt());
    }
}

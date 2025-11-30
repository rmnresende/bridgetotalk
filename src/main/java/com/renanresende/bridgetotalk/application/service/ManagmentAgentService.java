package com.renanresende.bridgetotalk.application.service;

import com.renanresende.bridgetotalk.application.mapper.AgentCommandMapper;
import com.renanresende.bridgetotalk.application.port.in.ManageAgentUseCase;
import com.renanresende.bridgetotalk.application.port.in.command.CreateAgentCommand;
import com.renanresende.bridgetotalk.application.port.out.AgentRepositoryPort;
import com.renanresende.bridgetotalk.application.port.out.CompanyRepositoryPort;
import com.renanresende.bridgetotalk.domain.Agent;
import com.renanresende.bridgetotalk.domain.AgentStatus;
import com.renanresende.bridgetotalk.domain.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ManagmentAgentService implements ManageAgentUseCase {

    private final AgentRepositoryPort repository;
    private final CompanyRepositoryPort companyRepository;
    private final AgentCommandMapper mapper;

    public ManagmentAgentService(AgentRepositoryPort repository,
                                 CompanyRepositoryPort companyRepository,
                                 AgentCommandMapper mapper) {
        this.repository = repository;
        this.companyRepository = companyRepository;
        this.mapper = mapper;
    }

    @Override
    public Agent create(CreateAgentCommand command) {

        companyRepository.findById(command.companyId())
                .orElseThrow(() -> new ResourceNotFoundException("Company not found"));

        var entity = mapper.toDomain(command);
        return repository.save(entity);
    }

    @Override
    public Agent getActiveAgent(UUID id, UUID companyId) {

        companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found"));

        return repository.findActiveAgentByIdAndCompanyId(id, companyId).
                orElseThrow(() -> new ResourceNotFoundException("Agent not found"));
    }

    @Override
    public void updateAgentStatus(UUID id, AgentStatus status) {

    }

    @Override
    public boolean deleteAgent(UUID id) {
        return false;
    }
}

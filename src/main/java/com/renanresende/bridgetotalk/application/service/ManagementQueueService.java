package com.renanresende.bridgetotalk.application.service;

import com.renanresende.bridgetotalk.adapter.in.web.dto.queue.QueueFilter;
import com.renanresende.bridgetotalk.application.mapper.QueueCommandMapper;
import com.renanresende.bridgetotalk.application.port.in.ManageQueueUseCase;
import com.renanresende.bridgetotalk.application.port.in.command.CreateQueueCommand;
import com.renanresende.bridgetotalk.application.port.in.command.LinkQueueAgentCommand;
import com.renanresende.bridgetotalk.application.port.in.command.UpdateQueueCommand;
import com.renanresende.bridgetotalk.application.port.out.AgentQueueRepositoryPort;
import com.renanresende.bridgetotalk.application.port.out.AgentRepositoryPort;
import com.renanresende.bridgetotalk.application.port.out.CompanyRepositoryPort;
import com.renanresende.bridgetotalk.application.port.out.QueueRepositoryPort;
import com.renanresende.bridgetotalk.domain.attendance.QueueNotFoundException;
import com.renanresende.bridgetotalk.domain.organization.CompanyNotFoundException;
import com.renanresende.bridgetotalk.domain.people.Agent;
import com.renanresende.bridgetotalk.domain.attendance.Queue;
import com.renanresende.bridgetotalk.domain.people.AgentNotFoundException;
import com.renanresende.bridgetotalk.domain.shared.exception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ManagementQueueService implements ManageQueueUseCase {

    private final QueueRepositoryPort queueRepository;
    private final CompanyRepositoryPort companyRepository;
    private final AgentQueueRepositoryPort agentQueueRepository;
    private final AgentRepositoryPort agentRepository;
    private final QueueCommandMapper mapper;

    @Override
    public Queue createQueue(CreateQueueCommand createQueueCommand) {

        validateQueuePreConditions(createQueueCommand.name(), createQueueCommand.companyId());

        var domain = mapper.fromCreateCommandtoDomain(createQueueCommand);
        return queueRepository.save(domain);
    }

    @Override
    public Queue updateQueue(UUID queueId, UpdateQueueCommand updateQueueCommand) {

        validateQueueUpdatePreConditions(queueId, updateQueueCommand.companyId(), updateQueueCommand.name());
        var domain = mapper.fromUpdateCommandtoDomain(queueId, updateQueueCommand);
        domain.update(updateQueueCommand.name(), updateQueueCommand.distributionStrategy());

        return queueRepository.save(domain);
    }

    @Override
    public List<Queue> filterQueuesByCompanyId(QueueFilter queueFilter, UUID companyId) {
        return queueRepository.filterQueuesByCompanyId(queueFilter, companyId);
    }

    @Override
    public List<Queue> getAllActiveQueuesFromCompany(UUID companyId) {
        return queueRepository.findAllActiveQueuesByCompanyId(companyId);
    }

    @Override
    public void deleteQueue(UUID queueId, UUID companyId) {
        queueRepository.findByIdAndCompanyId(queueId, companyId)
                .orElseThrow(() -> new QueueNotFoundException(queueId));

        queueRepository.deleteQueue(queueId, Instant.now());
    }

    @Override
    public List<Agent> findAgentsByQueueId(UUID queueId) {
        return agentQueueRepository.findAgentsByQueueId(queueId);
    }

    @Override
    public void linkAgentToQueue(LinkQueueAgentCommand linkQueueAgentCommand) {

        validateIfBelongSameCompany(linkQueueAgentCommand.agentId(), linkQueueAgentCommand.queueId());

        agentQueueRepository.linkAgentToQueue(linkQueueAgentCommand.agentId(),
                                              linkQueueAgentCommand.queueId(),
                                              linkQueueAgentCommand.priority()
        );
    }

    @Override
    public void unlinkAgentFromQueue(UUID agentId, UUID queueId) {

        validateIfBelongSameCompany(agentId, queueId);
        agentQueueRepository.unlinkAgentFromQueue(agentId, queueId);
    }

    private void validateQueuePreConditions(String name, UUID companyId) {
        companyRepository.findById(companyId)
                .orElseThrow(() -> new CompanyNotFoundException(companyId));

        if (existisQueueInCompanyWithSameName(name, companyId)) {
            throw new ResourceAlreadyExistsException("Already exists a queue with same name in this company");
        }
    }

    private void validateIfBelongSameCompany(UUID agentId, UUID queueId) {
        var existingQueue = queueRepository.findById(queueId)
                .orElseThrow(() -> new QueueNotFoundException(queueId));

        var existingAgent = agentRepository.findById(agentId)
                .orElseThrow(() -> new AgentNotFoundException(agentId));

        if (!existingAgent.getCompanyId().equals(existingQueue.getCompanyId())) {
            throw new BusinessException("Agent and Queue must belong to the same company");
        }
    }

    private void validateQueueUpdatePreConditions(UUID queueId, UUID companyId, String name) {

        queueRepository.findByIdAndCompanyId(queueId, companyId)
                .orElseThrow(() -> new QueueNotFoundException(queueId));

        if (existisQueueInCompanyWithSameName(name, companyId)) {
            throw new ResourceAlreadyExistsException("Already exists a queue with same name in this company");
        }
    }

    private boolean existisQueueInCompanyWithSameName(String name, UUID companyId) {
        return queueRepository.findByCompanyIdAndName(companyId, name).isPresent();
    }
}

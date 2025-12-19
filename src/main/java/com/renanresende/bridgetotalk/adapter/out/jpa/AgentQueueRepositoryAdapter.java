package com.renanresende.bridgetotalk.adapter.out.jpa;

import com.renanresende.bridgetotalk.adapter.out.jpa.entity.AgentQueueId;
import com.renanresende.bridgetotalk.adapter.out.jpa.entity.AgentQueueJpaEntity;
import com.renanresende.bridgetotalk.adapter.out.jpa.mapper.AgentJpaMapper;
import com.renanresende.bridgetotalk.adapter.out.jpa.mapper.QueueJpaMapper;
import com.renanresende.bridgetotalk.application.port.out.AgentQueueRepositoryPort;
import com.renanresende.bridgetotalk.domain.Queue;
import org.springframework.stereotype.Repository;
import com.renanresende.bridgetotalk.domain.Agent;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public class AgentQueueRepositoryAdapter implements AgentQueueRepositoryPort {

    private final SpringDataAgentQueueRepository repository;
    private final AgentJpaMapper agentMapper;
    private final QueueJpaMapper queueMapper;

    public AgentQueueRepositoryAdapter(SpringDataAgentQueueRepository repository, AgentJpaMapper agentMapper, QueueJpaMapper queueMapper) {
        this.repository = repository;
        this.agentMapper = agentMapper;
        this.queueMapper = queueMapper;
    }

    @Override
    public List<Queue> findQueuesByAgentId(UUID agentId) {
        return repository.findByAgent_Id(agentId)
                .stream()
                .map(AgentQueueJpaEntity::getQueue)
                .map(queueMapper::toDomain)
                .toList();
    }

    @Override
    public List<Agent> findAgentsByQueueId(UUID queueId) {
        return repository.findByQueue_Id(queueId)
                .stream()
                .map(AgentQueueJpaEntity::getAgent)
                .map(agentMapper::toDomain)
                .toList();
    }

    @Override
    public void linkAgentToQueue(Agent agent, Queue queue, int priority) {

        var agentEntity = agentMapper.toEntity(agent);
        var queueEntity = queueMapper.toEntity(queue);

        var entity = new AgentQueueJpaEntity(
                new AgentQueueId(agent.getId(), queue.getId()),
                agentEntity, // set via reference
                queueEntity,
                priority,
                Instant.now()
        );
        repository.save(entity);
    }

    @Override
    public void unlinkAgentFromQueue(UUID agentId, UUID queueId) {
        repository.deleteById(new AgentQueueId(agentId, queueId));
    }
}

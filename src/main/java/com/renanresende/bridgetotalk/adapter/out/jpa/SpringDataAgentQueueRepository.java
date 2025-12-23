package com.renanresende.bridgetotalk.adapter.out.jpa;

import com.renanresende.bridgetotalk.adapter.out.jpa.entity.AgentQueueId;
import com.renanresende.bridgetotalk.adapter.out.jpa.entity.AgentQueueJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SpringDataAgentQueueRepository
        extends JpaRepository<AgentQueueJpaEntity, AgentQueueId> {

    List<AgentQueueJpaEntity> findByAgentId(UUID agentId);

    List<AgentQueueJpaEntity> findByQueueId(UUID queueId);
}


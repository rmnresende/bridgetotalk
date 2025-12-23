package com.renanresende.bridgetotalk.application.port.out;

import com.renanresende.bridgetotalk.domain.attendance.Queue;
import com.renanresende.bridgetotalk.domain.people.Agent;

import java.util.List;
import java.util.UUID;

public interface AgentQueueRepositoryPort {

    void linkAgentToQueue(UUID agentId, UUID queueId, int priority);

    void unlinkAgentFromQueue(UUID agentId, UUID queueId);

    List<Queue> findQueuesByAgentId(UUID agentId);

    List<Agent> findAgentsByQueueId(UUID queueId);
}

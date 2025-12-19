package com.renanresende.bridgetotalk.application.port.in;

import com.renanresende.bridgetotalk.adapter.in.web.dto.queue.QueueAgentLinkDto;
import com.renanresende.bridgetotalk.adapter.in.web.dto.queue.QueueFilter;
import com.renanresende.bridgetotalk.application.port.in.command.CreateQueueCommand;
import com.renanresende.bridgetotalk.application.port.in.command.LinkQueueAgentCommand;
import com.renanresende.bridgetotalk.application.port.in.command.UpdateQueueCommand;
import com.renanresende.bridgetotalk.domain.Agent;
import com.renanresende.bridgetotalk.domain.Queue;

import java.util.List;
import java.util.UUID;

public interface ManageQueueUseCase {

    Queue createQueue(CreateQueueCommand createQueueCommand);

    Queue updateQueue(UUID queueId, UpdateQueueCommand updateQueueCommand);

    List<Queue> filterQueuesByCompanyId(QueueFilter queueFilter, UUID companyId);

    List<Queue> getAllActiveQueuesFromCompany(UUID companyId);

    void deleteQueue(UUID queueId, UUID companyId);

    List<Agent> findAgentsByQueueId(UUID queueId);

    void linkAgentToQueue(LinkQueueAgentCommand linkQueueAgentCommand);

    void unlinkAgentFromQueue(UUID agentId, UUID queueId);
}
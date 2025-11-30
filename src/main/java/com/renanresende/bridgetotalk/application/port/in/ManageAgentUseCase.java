package com.renanresende.bridgetotalk.application.port.in;

import com.renanresende.bridgetotalk.application.port.in.command.CreateAgentCommand;
import com.renanresende.bridgetotalk.domain.Agent;
import com.renanresende.bridgetotalk.domain.AgentStatus;

import java.util.UUID;

public interface ManageAgentUseCase {

    Agent create(CreateAgentCommand command);

    Agent getActiveAgent(UUID id, UUID companyId);

    void updateAgentStatus(UUID id, AgentStatus status);

//    void associateAgentToQueue(AssociateAgentToQueueCommand command);

    boolean deleteAgent(UUID id);

}
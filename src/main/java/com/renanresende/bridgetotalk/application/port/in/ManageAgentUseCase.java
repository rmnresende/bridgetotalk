package com.renanresende.bridgetotalk.application.port.in;

import com.renanresende.bridgetotalk.adapter.in.web.dto.AgentFilter;
import com.renanresende.bridgetotalk.application.port.in.command.CreateAgentCommand;
import com.renanresende.bridgetotalk.domain.Agent;
import com.renanresende.bridgetotalk.domain.AgentStatus;

import java.util.List;
import java.util.UUID;

public interface ManageAgentUseCase {

    Agent create(CreateAgentCommand command);

    Agent getActiveAgent(UUID id, UUID companyId);

    List<Agent> filterActiveAgentsByCompanyId(AgentFilter agentFilter, UUID companyId);

    void updateAgentStatus(UUID id, UUID companyId, AgentStatus status);

//    void associateAgentToQueue(AssociateAgentToQueueCommand command);

    void deleteAgent(UUID id, UUID companyId);

}
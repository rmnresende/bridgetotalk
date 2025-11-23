package com.renanresende.bridgetotalk.application.port.in;

import com.renanresende.bridgetotalk.domain.Agent;
import com.renanresende.bridgetotalk.domain.AgentRole;
import java.util.UUID;

public interface ManageAgentUseCase {

    Agent createAgent(CreateAgentCommand command);

    void updateAgentStatus(UpdateAgentStatusCommand command);

    void associateAgentToQueue(AssociateAgentToQueueCommand command);

    // Commands para encapsular dados:
    class CreateAgentCommand {
        private final UUID companyId;
        private final String name;
        private final String email;
        private final String password;
        private final AgentRole role;

        public CreateAgentCommand(UUID companyId, String name, String email, String password, AgentRole role) {
            this.companyId = companyId;
            this.name = name;
            this.email = email;
            this.password = password;
            this.role = role;
        }

        // Construtor, getters
    }

    class UpdateAgentStatusCommand {
        private final UUID agentId;
        private final String newStatus; // Usaremos String/Enum

        public UpdateAgentStatusCommand(UUID agentId, String newStatus) {
            this.agentId = agentId;
            this.newStatus = newStatus;
        }

        // Construtor, getters
    }

    class AssociateAgentToQueueCommand {
        private final UUID agentId;
        private final UUID queueId;

        public AssociateAgentToQueueCommand(UUID agentId, UUID queueId) {
            this.agentId = agentId;
            this.queueId = queueId;
        }

        // Construtor, getters
    }
}
package com.renanresende.bridgetotalk.application.port.in;

import com.renanresende.bridgetotalk.domain.Queue;
import com.renanresende.bridgetotalk.domain.DistributionStrategy;
import java.util.UUID;

public interface ManageQueueUseCase {

    Queue createQueue(CreateQueueCommand command);

    // Commands para encapsular dados:
    class CreateQueueCommand {
        private final UUID companyId;
        private final String name;
        private final DistributionStrategy strategy;

        public CreateQueueCommand(UUID companyId, String name, DistributionStrategy strategy) {
            this.companyId = companyId;
            this.name = name;
            this.strategy = strategy;
        }

        // Construtor, getters
    }

    // Outros comandos para Update, Delete, etc.
}
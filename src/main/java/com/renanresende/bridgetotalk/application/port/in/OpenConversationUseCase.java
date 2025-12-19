package com.renanresende.bridgetotalk.application.port.in;

import com.renanresende.bridgetotalk.domain.attendance.Conversation;

import java.util.UUID;

public interface OpenConversationUseCase {

    Conversation handleIncomingMessage(IncomingMessageCommand command);

    // Command que representa a mensagem recebida de um Adaptador (e.g., WhatsAppAdapter)
    class IncomingMessageCommand {
        private final UUID companyId; // Para multi-tenancy
        private final String customerPhone;
        private final String channelType;
        private final String content;

        public IncomingMessageCommand(UUID companyId, String customerPhone, String channelType, String content) {
            this.companyId = companyId;
            this.customerPhone = customerPhone;
            this.channelType = channelType;
            this.content = content;
        }

        // Construtor, getters
    }
}
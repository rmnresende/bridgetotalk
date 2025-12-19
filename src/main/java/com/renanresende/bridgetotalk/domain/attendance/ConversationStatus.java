package com.renanresende.bridgetotalk.domain.attendance;

public enum ConversationStatus {
    WAITING_IN_QUEUE("Waiting in Queue"), // A conversa chegou e está aguardando um agente.
    IN_PROGRESS("In Progress"),           // Um agente está ativo na conversa.
    WAITING_FOR_CUSTOMER("Waiting for Customer"), // Agente respondeu, aguardando a próxima mensagem do cliente.
    PENDING("Pending"),                   // Pausado ou em espera por um terceiro.
    CLOSED("Closed");                     // Conversa finalizada e arquivada.

    private final String description;

    ConversationStatus(String description) {
        this.description = description;
    }
    // ... (Construtor e getter)
}
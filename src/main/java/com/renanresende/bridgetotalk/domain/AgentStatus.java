package com.renanresende.bridgetotalk.domain;

public enum AgentStatus {
    AVAILABLE("Available"),       // Pode receber novas conversas (rotação).
    BUSY("Busy"),                 // Está em uma conversa, mas ainda pode ser elegível dependendo da estratégia.
    PAUSED("Paused"),             // Indisponível (ex: almoço, reunião). Não recebe novas conversas.
    OFFLINE("Offline");           // Deslogado do sistema.

    private final String description;

    AgentStatus(String description) {
        this.description = description;
    }
    // ... (Construtor e getter)
}
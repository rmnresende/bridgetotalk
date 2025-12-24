package com.renanresende.bridgetotalk.domain.people;

public enum AgentStatus {
    AVAILABLE("Available"),       // Pode receber novas conversas (rotação).
    BUSY("Busy"),                 // Está em uma conversa, mas ainda pode ser elegível dependendo da estratégia.
    PAUSED("Paused"),             // Indisponível (ex: almoço, reunião). Não recebe novas conversas.
    OFFLINE("Offline");           // Deslogado do sistema.

    private final String description;

    AgentStatus(String description) {
        this.description = description;
    }

    public static AgentStatus from(String value) {

        if (value == null || value.isBlank()) {
            return null;
        }

        try {
            return AgentStatus.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("Valor inválido após validação: " + value);
        }
    }
}
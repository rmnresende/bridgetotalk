package com.renanresende.bridgetotalk.domain;

public enum AgentRole {
    ADMIN("Admin"),       // Acesso total, pode gerenciar empresas, planos, e configurações globais.
    MANAGER("Manager"),   // Gerente de equipe. Pode gerenciar agentes, filas e relatórios da sua empresa.
    AGENT("Agent");       // Atendente padrão. Pode gerenciar suas próprias conversas.

    private final String description;

    AgentRole(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

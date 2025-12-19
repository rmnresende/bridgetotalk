package com.renanresende.bridgetotalk.domain.attendance;

public enum DistributionStrategy {
    LEAST_BUSY("Least Busy"),        // Direciona para o agente com o menor número de conversas ativas.
    ROUND_ROBIN("Round Robin"),      // Roda por todos os agentes elegíveis, sequencialmente.
    LONGEST_AVAILABLE("Longest Available"), // Direciona para o agente que está disponível há mais tempo.
    PRIORITY("Priority Based");       // Direciona com base em uma pontuação de prioridade do agente.

    private final String description;

    DistributionStrategy(String description) {
        this.description = description;
    }
}
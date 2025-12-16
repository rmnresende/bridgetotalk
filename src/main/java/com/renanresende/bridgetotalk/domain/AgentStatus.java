package com.renanresende.bridgetotalk.domain;

import com.renanresende.bridgetotalk.domain.exception.InvalidEnumValueException;

import java.util.Arrays;

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

        return Arrays.stream(values())
                .filter(s -> s.name().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() ->
                        new InvalidEnumValueException(
                                "status",
                                value,
                                valuesAsString()
                        )
                );
    }

    private static String valuesAsString() {
        return Arrays.stream(values())
                .map(Enum::name)
                .toList()
                .toString();
    }

}
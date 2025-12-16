package com.renanresende.bridgetotalk.adapter.in.web.dto;

import com.renanresende.bridgetotalk.domain.AgentStatus;

import java.util.Optional;

public record AgentFilter (
        Optional<String> name,
        Optional<String> email,
        Optional<AgentStatus> status,
        Optional<String> sortBy,
        Optional<String> sortDirection,
        boolean findInactiveAgents
) {
}

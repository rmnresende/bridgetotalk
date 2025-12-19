package com.renanresende.bridgetotalk.adapter.in.web.dto.agent;

import com.renanresende.bridgetotalk.adapter.in.web.dto.QueryOptions;
import com.renanresende.bridgetotalk.domain.people.AgentStatus;

import java.util.Optional;

public record AgentFilter (
        Optional<String> name,
        Optional<String> email,
        Optional<AgentStatus> status,
       QueryOptions queryOptions
) {
}

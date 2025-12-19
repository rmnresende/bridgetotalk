package com.renanresende.bridgetotalk.domain.people;

import com.renanresende.bridgetotalk.domain.shared.exception.ResourceNotFoundException;

import java.util.UUID;

public class AgentNotFoundException extends ResourceNotFoundException {

    public AgentNotFoundException(UUID agentId) {
        super("Agent not found with id: " + agentId + "in the company");
    }

    public AgentNotFoundException(String email) {
        super("Agent not found with email: " + email + "in this company");
    }
}

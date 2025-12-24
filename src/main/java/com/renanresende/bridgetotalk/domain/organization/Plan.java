package com.renanresende.bridgetotalk.domain.organization;

import lombok.Getter;

@Getter
public enum Plan {

    FREE(1, 3, 1),
    BASIC(3, 10, 3),
    PRO(30, 50, 10),
    ENTERPRISE(100, 100, 20);

    private int defaultPlanMaxAgents;
    private int defaultPlanMaxQueues;
    private int maxConcurrentConversationsPerAgent;

    Plan(int maxAgents ,int maxQueues, int maxConcurrentConversationsPerAgent) {
        this.defaultPlanMaxAgents = maxAgents;
        this.defaultPlanMaxQueues = maxQueues;
        this.maxConcurrentConversationsPerAgent = maxConcurrentConversationsPerAgent;
    }
}

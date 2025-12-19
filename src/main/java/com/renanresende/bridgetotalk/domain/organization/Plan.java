package com.renanresende.bridgetotalk.domain.organization;

import lombok.Getter;

@Getter
public enum Plan {

    FREE(1, 3),
    BASIC(3, 2),
    PRO(30, 10),
    ENTERPRISE(100, 60);

    private int defaultPlanMaxAgents;
    private int defaultPlanMaxQueues;

    Plan(int maxAgents ,int maxQueues) {
        this.defaultPlanMaxAgents = maxAgents;
        this.defaultPlanMaxQueues = maxQueues;
    }
}

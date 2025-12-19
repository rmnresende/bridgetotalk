package com.renanresende.bridgetotalk.adapter.out.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AgentQueueId implements Serializable {

    @Column(name = "agent_id")
    private UUID agentId;

    @Column(name = "queue_id")
    private UUID queueId;
}

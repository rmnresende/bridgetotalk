package com.renanresende.bridgetotalk.adapter.out.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "agent_queues")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AgentQueueJpaEntity {

    @EmbeddedId
    private AgentQueueId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("agentId")
    @JoinColumn(name = "agent_id", nullable = false)
    private AgentJpaEntity agent;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("queueId")
    @JoinColumn(name = "queue_id", nullable = false)
    private QueueJpaEntity queue;

    @Column(nullable = false)
    private int priority;

    @Column(name = "added_at", nullable = false)
    private Instant addedAt;
}

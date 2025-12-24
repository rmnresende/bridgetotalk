package com.renanresende.bridgetotalk.adapter.out.jpa.entity;

import com.renanresende.bridgetotalk.domain.people.AgentRole;
import com.renanresende.bridgetotalk.domain.people.AgentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "agents")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class AgentJpaEntity {

    @Id
    private  UUID id;

    @Column(name = "company_id", columnDefinition = "UUID", nullable = false)
    private  UUID companyId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private AgentRole role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private AgentStatus status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private  Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    @Column(name = "active_conversations", nullable = false)
    private int activeConversations;

    @OneToMany(
            mappedBy = "agent",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<AgentQueueJpaEntity> queues = new HashSet<>();
}

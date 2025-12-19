package com.renanresende.bridgetotalk.adapter.out.jpa.entity;

import com.renanresende.bridgetotalk.domain.DistributionStrategy;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "queues")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QueueJpaEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(name = "company_id", nullable = false)
    private UUID companyId;

    @Enumerated(EnumType.STRING)
    @Column(name = "distribution_strategy", nullable = false)
    private DistributionStrategy distributionStrategy;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    @OneToMany(
            mappedBy = "queue",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<AgentQueueJpaEntity> agents = new HashSet<>();
}

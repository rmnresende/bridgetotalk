package com.renanresende.bridgetotalk.adapter.out.jpa.entity;

import com.renanresende.bridgetotalk.domain.organization.Plan;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "company_settings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanySettingsJpaEntity {

    @Id

    @Column(name = "company_id")
    private UUID companyId;

    @Column(name = "max_agents", nullable = false)
    private int maxAgents;

    @Column(name = "max_queues", nullable = false)
    private int maxQueues;

    @Column(nullable = false, length = 50)
    private String timezone;

    @Column(nullable = false, length = 10)
    private String language;

    @OneToOne(cascade = {CascadeType.PERSIST})
    @MapsId //settings é uma extensão de Company
    @JoinColumn(name = "company_id")
    private CompanyJpaEntity company;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private Plan plan;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Column(name = "max_concurrent_conversations_per_agent", nullable = false)
    private int maxConcurrentConversationsPerAgent;
}

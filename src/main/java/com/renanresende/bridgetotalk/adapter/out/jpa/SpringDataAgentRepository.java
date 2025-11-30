package com.renanresende.bridgetotalk.adapter.out.jpa;

import com.renanresende.bridgetotalk.adapter.out.jpa.entity.AgentJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface SpringDataAgentRepository extends JpaRepository<AgentJpaEntity, UUID> {

    @Query("SELECT a FROM AgentJpaEntity a WHERE a.id = :id and a.companyId = :companyId and a.deletedAt is null")
    Optional<AgentJpaEntity> findActiveAgentByIdAndCompanyId(UUID id, UUID companyId);

    @Query("SELECT a FROM AgentJpaEntity a WHERE a.email = :email and a.companyId = :companyId and a.deletedAt is null")
    Optional<AgentJpaEntity> findActiveByEmailAndCompanyId(String email, UUID companyId);
}

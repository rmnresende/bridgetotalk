package com.renanresende.bridgetotalk.adapter.out.jpa;

import com.renanresende.bridgetotalk.adapter.out.jpa.entity.AgentJpaEntity;
import com.renanresende.bridgetotalk.domain.people.AgentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SpringDataAgentRepository extends JpaRepository<AgentJpaEntity, UUID>, JpaSpecificationExecutor<AgentJpaEntity> {

    @Query("SELECT a FROM AgentJpaEntity a WHERE a.id = :id and a.companyId = :companyId and a.deletedAt is null")
    Optional<AgentJpaEntity> findActiveAgentByIdAndCompanyId(UUID id, UUID companyId);

    @Query("SELECT a FROM AgentJpaEntity a WHERE a.email = :email and a.companyId = :companyId and a.deletedAt is null")
    Optional<AgentJpaEntity> findActiveByEmailAndCompanyId(String email, UUID companyId);

    @Query("SELECT a FROM AgentJpaEntity a WHERE a.companyId = :companyId and a.deletedAt is null")
    List<AgentJpaEntity> findAllActiveAgentsByCompanyId(UUID companyId);

    @Modifying
    @Query("UPDATE AgentJpaEntity a SET a.status = :status, a.updatedAt = :updatedAt WHERE a.id = :id")
    int updateStatus(@Param("id") UUID id, @Param("status") AgentStatus status, @Param("updatedAt") Instant updatedAt);

    @Modifying
    @Query("UPDATE AgentJpaEntity a SET a.deletedAt = :deletedAt WHERE a.id = :id")
    int deleteAgent(@Param("id") UUID id, @Param("deletedAt") Instant deletedAt);
}

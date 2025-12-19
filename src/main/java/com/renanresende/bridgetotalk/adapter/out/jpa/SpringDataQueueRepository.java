package com.renanresende.bridgetotalk.adapter.out.jpa;

import com.renanresende.bridgetotalk.adapter.out.jpa.entity.QueueJpaEntity;
import com.renanresende.bridgetotalk.domain.Queue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SpringDataQueueRepository extends JpaRepository<QueueJpaEntity, UUID>, JpaSpecificationExecutor<QueueJpaEntity> {

    @Query("SELECT q FROM QueueJpaEntity q WHERE q.companyId = :companyId and q.deletedAt is null")
    List<QueueJpaEntity> findAllActiveQueuesByCompanyId(UUID companyId);

    Optional<QueueJpaEntity> findByCompanyIdAndName(UUID companyId, String name);

    Optional<QueueJpaEntity> findByIdAndCompanyId(UUID id, UUID companyId);

    @Modifying
    @Query("UPDATE QueueJpaEntity q SET q.deletedAt = :deletedAt WHERE q.id = :id")
    int deleteQueue(@Param("id") UUID id, @Param("deletedAt") Instant deletedAt);
}

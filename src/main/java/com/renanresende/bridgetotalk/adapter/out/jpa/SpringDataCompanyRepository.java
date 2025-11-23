package com.renanresende.bridgetotalk.adapter.out.jpa;

import com.renanresende.bridgetotalk.adapter.out.jpa.entity.CompanyJpaEntity;
import com.renanresende.bridgetotalk.domain.CompanyStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public interface SpringDataCompanyRepository extends JpaRepository<CompanyJpaEntity, UUID> {

    Optional<CompanyJpaEntity> findBySlug(String slug);

    boolean existsByEmailOrSlug(String email, String slug);

    boolean existsBySlug(String slug);

    @Modifying
    @Query("UPDATE CompanyJpaEntity c SET c.status = :status, c.updatedAt = :updatedAt WHERE c.id = :id")
    int updateStatusAndUpdatedAtById(@Param("id") UUID id, @Param("status") CompanyStatus status, @Param("updatedAt") Instant updatedAt);
}

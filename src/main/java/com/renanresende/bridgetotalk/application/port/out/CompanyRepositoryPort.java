package com.renanresende.bridgetotalk.application.port.out;

import com.renanresende.bridgetotalk.domain.organization.Company;
import com.renanresende.bridgetotalk.domain.organization.CompanySettings;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CompanyRepositoryPort {

    @Transactional
    Company saveNew(Company company);

    @Transactional
    Company update(Company company);

    @Transactional
    CompanySettings updateSettings(Company company);

    Optional<Company> findById(UUID id);

    Optional<Company> findBySlug(String slug);

    List<Company> findAll();

    boolean inactiveCompany(UUID id);

    boolean existsBySlug(String slug);
}

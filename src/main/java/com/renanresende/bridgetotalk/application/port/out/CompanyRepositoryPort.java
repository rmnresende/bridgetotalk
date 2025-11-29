package com.renanresende.bridgetotalk.application.port.out;

import com.renanresende.bridgetotalk.domain.exception.BusinessException;
import com.renanresende.bridgetotalk.domain.Company;
import com.renanresende.bridgetotalk.domain.CompanySettings;
import com.renanresende.bridgetotalk.domain.CompanyStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CompanyRepositoryPort {

    Company save(Company company);

    CompanySettings updateCompanySettings(Company company) throws BusinessException;

    void updateStatus(UUID companyId, CompanyStatus status);

    Optional<Company> findById(UUID id);

    Optional<Company> findBySlug(String slug);

    List<Company> findAll();

    void delete(UUID id);

    boolean existsBySlug(String slug);
}

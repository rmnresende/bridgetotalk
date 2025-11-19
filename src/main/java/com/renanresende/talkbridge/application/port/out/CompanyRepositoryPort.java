package com.renanresende.talkbridge.application.port.out;

import com.renanresende.talkbridge.domain.Company;
import java.util.Optional;
import java.util.UUID;

public interface CompanyRepositoryPort {

    Company save(Company company);

    Optional<Company> findById(UUID id);

    Optional<Company> findBySlug(String slug);

    // Contrato para verificar se uma empresa existe pelo email ou slug
    boolean existsByEmailOrSlug(String email, String slug);
}
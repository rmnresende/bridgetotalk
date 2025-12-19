package com.renanresende.bridgetotalk.adapter.out.jpa;

import com.renanresende.bridgetotalk.adapter.out.jpa.mapper.CompanyJpaMapper;
import com.renanresende.bridgetotalk.application.port.out.CompanyRepositoryPort;
import com.renanresende.bridgetotalk.domain.organization.Company;
import com.renanresende.bridgetotalk.domain.organization.CompanySettings;
import com.renanresende.bridgetotalk.domain.organization.CompanyNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class CompanyRepositoryAdapter implements CompanyRepositoryPort {

    private final SpringDataCompanyRepository companyRepo;
    private final SpringDataCompanySettingsRepository settingsRepository;
    private final CompanyJpaMapper mapper;

    public CompanyRepositoryAdapter(
            SpringDataCompanyRepository companyRepo,
            SpringDataCompanySettingsRepository settingsRepository,
            CompanyJpaMapper mapper
    ) {
        this.companyRepo = companyRepo;
        this.settingsRepository = settingsRepository;
        this.mapper = mapper;
    }


    @Override
    public Company saveNew(Company company) {

        var companyEntity = mapper.toEntity(company);
        companyEntity = companyRepo.save(companyEntity);

        if (company.getSettings() != null) {

            var settingsEntity = mapper.toSettingsEntityFromDomain(company.getSettings());
            settingsEntity.setCompany(companyEntity);

            settingsRepository.save(settingsEntity);
            companyEntity.setSettings(settingsEntity);
        }

        return mapper.toDomain(companyEntity);
    }

    @Transactional
    @Override
    public Company update(Company company) {

        var entity =
                companyRepo.findById(company.getId())
                        .orElseThrow(() -> new CompanyNotFoundException(company.getId()));

        mapper.updateEntityFromDomain(company, entity);

        if (company.getSettings() != null && entity.getSettings() != null) {
            mapper.updateSettingsEntityFromDomain(
                    company.getSettings(),
                    entity.getSettings()
            );
        }

        return mapper.toDomain(entity);
    }


    @Transactional
    @Override
    public CompanySettings updateSettings(Company company) {

        var entity =
                companyRepo.findById(company.getId())
                        .orElseThrow(() -> new CompanyNotFoundException(company.getId()));

        mapper.updateSettingsEntityFromDomain(
                company.getSettings(),
                entity.getSettings()
        );

        return mapper.companySettingsJpaEntityToCompanySettings(entity.getSettings());
    }


    @Override
    public Optional<Company> findById(UUID id) {
        return companyRepo.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<Company> findBySlug(String slug) {
        return Optional.empty();
    }

    @Override
    public List<Company> findAll() {
        return companyRepo.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public boolean inactiveCompany(UUID id) {
        return false;
    }

    @Override
    public boolean existsBySlug(String slug) {
        return companyRepo.existsBySlug(slug);
    }
}

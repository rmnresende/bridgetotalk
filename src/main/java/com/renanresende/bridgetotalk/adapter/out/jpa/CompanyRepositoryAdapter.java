package com.renanresende.bridgetotalk.adapter.out.jpa;

import com.renanresende.bridgetotalk.adapter.out.jpa.entity.CompanyJpaEntity;
import com.renanresende.bridgetotalk.adapter.out.jpa.entity.CompanySettingsJpaEntity;
import com.renanresende.bridgetotalk.adapter.out.jpa.mapper.CompanyJpaMapper;
import com.renanresende.bridgetotalk.application.port.out.CompanyRepositoryPort;
import com.renanresende.bridgetotalk.domain.Company;
import com.renanresende.bridgetotalk.domain.CompanySettings;
import com.renanresende.bridgetotalk.domain.CompanyStatus;
import com.renanresende.bridgetotalk.domain.exception.BusinessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component
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

        CompanyJpaEntity entity =
                companyRepo.findById(company.getId())
                        .orElseThrow(() -> new IllegalStateException("Company not found"));

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

        CompanyJpaEntity entity =
                companyRepo.findById(company.getId())
                        .orElseThrow(() -> new IllegalStateException("Company not found"));

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

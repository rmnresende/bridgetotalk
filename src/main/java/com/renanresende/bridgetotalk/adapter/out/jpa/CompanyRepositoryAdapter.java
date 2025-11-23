package com.renanresende.bridgetotalk.adapter.out.jpa;

import com.renanresende.bridgetotalk.adapter.out.jpa.entity.CompanyJpaEntity;
import com.renanresende.bridgetotalk.adapter.out.jpa.entity.CompanySettingsJpaEntity;
import com.renanresende.bridgetotalk.adapter.out.jpa.mapper.CompanyJpaMapper;
import com.renanresende.bridgetotalk.application.port.out.CompanyRepositoryPort;
import com.renanresende.bridgetotalk.commom.BusinessException;
import com.renanresende.bridgetotalk.domain.Company;
import com.renanresende.bridgetotalk.domain.CompanySettings;
import com.renanresende.bridgetotalk.domain.CompanyStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;

@Component
@RequiredArgsConstructor
public class CompanyRepositoryAdapter implements CompanyRepositoryPort {

    private final SpringDataCompanyRepository jpaRepository;
    private final SpringDataCompanySettingsRepository settingsRepo;
    private final CompanyJpaMapper jpaMapper;

    @Override
    public Company save(Company company) {
        // Assume que 'company' é o estado final desejado
        var entity = jpaMapper.toEntity(company);
        return jpaMapper.toDomain(jpaRepository.save(entity));
    }

    @Override
    public CompanySettings updateSettings(CompanySettings settings, Company company)throws BusinessException {
        var settingsEntity = jpaMapper.toSettingsEntity(settings, company);
        var saved = settingsRepo.save(settingsEntity);
        return jpaMapper.companySettingsJpaEntityToCompanySettings(saved);
    }

    @Override
    @Transactional
    public void updateStatus(UUID companyId, CompanyStatus status) {
        jpaRepository.updateStatusAndUpdatedAtById(companyId, status, Instant.now());
    }

    @Override
    public Optional<Company> findById(UUID id) {

        var optionalCompanyJpaEntity = jpaRepository.findById(id);

        if (optionalCompanyJpaEntity.isPresent()) {
            return Optional.of(jpaMapper.toDomain(optionalCompanyJpaEntity.get()));
        }

        return Optional.empty();
    }

    @Override
    public Optional<Company> findBySlug(String slug) {
        Optional<CompanyJpaEntity> optionalCompanyJpaEntity = jpaRepository.findBySlug(slug);

        if (optionalCompanyJpaEntity.isPresent()) {
            var companyJpaEntity = optionalCompanyJpaEntity.get();
            var settings = settingsRepo.findById(companyJpaEntity.getId()).orElse(null);
            companyJpaEntity.setSettings(settings);
            return Optional.of(jpaMapper.toDomain(companyJpaEntity));
        }

        return Optional.empty();
    }

    @Override
    public List<Company> findAll() {

        var companiesJpa = jpaRepository.findAll();
        var companiesDomain = new ArrayList<Company>();
        companiesJpa.forEach(c -> {

            var settings = settingsRepo.findById(c.getId()).orElse(null);
            c.setSettings(settings);
            companiesDomain.add(jpaMapper.toDomain(c));
        });

        return companiesDomain;
    }

    @Override
    public void delete(UUID id) {
        settingsRepo.deleteById(id);
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean existsBySlug(String slug) {
        return jpaRepository.existsBySlug(slug);
    }
}

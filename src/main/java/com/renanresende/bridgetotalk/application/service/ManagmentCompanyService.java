package com.renanresende.bridgetotalk.application.service;

import com.renanresende.bridgetotalk.application.port.in.ManageCompanyUseCase;
import com.renanresende.bridgetotalk.application.port.in.command.UpdateCompanyCommand;
import com.renanresende.bridgetotalk.application.port.in.command.UpdateCompanySettingsCommand;
import com.renanresende.bridgetotalk.application.port.out.CompanyRepositoryPort;
import com.renanresende.bridgetotalk.domain.exception.BusinessException;
import com.renanresende.bridgetotalk.domain.Company;
import com.renanresende.bridgetotalk.domain.CompanySettings;
import com.renanresende.bridgetotalk.domain.CompanyStatus;
import com.renanresende.bridgetotalk.domain.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ManagmentCompanyService implements ManageCompanyUseCase {

    private final CompanyRepositoryPort repository;

    public ManagmentCompanyService(CompanyRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public Company create(Company company) {
        return repository.saveNew(company);
    }

    @Override
    public Company update(UpdateCompanyCommand command) {

        Company existing = repository.findById(command.id())
                .orElseThrow(() -> new ResourceNotFoundException("Company not found"));

        existing.update(
                command.name(),
                command.slug(),
                command.email(),
                command.phone(),
                command.document()
        );

        return repository.update(existing);
    }

    @Override
    public CompanySettings updateSettings(
            UUID companyId,
            UpdateCompanySettingsCommand command
    ) {

        Company existing = repository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found"));

        existing.getSettings().applyUpdate(command);

        return repository.updateSettings(existing);
    }

    @Override
    public Company get(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found"));
    }

    @Override
    public List<Company> list() {
        return repository.findAll();
    }

    @Override
    public Company changeStatus(UUID companyId, CompanyStatus newStatus) {

        Company company = repository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found"));

        company.changeStatus(newStatus);

        return repository.update(company);
    }
}


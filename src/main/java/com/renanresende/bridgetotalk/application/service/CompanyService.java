package com.renanresende.bridgetotalk.application.service;

import com.renanresende.bridgetotalk.application.mapper.CompanyCommandMapper;
import com.renanresende.bridgetotalk.application.port.in.ManageCompanyUseCase;
import com.renanresende.bridgetotalk.application.port.in.command.UpdateCompanyCommand;
import com.renanresende.bridgetotalk.application.port.out.CompanyRepositoryPort;
import com.renanresende.bridgetotalk.commom.BusinessException;
import com.renanresende.bridgetotalk.domain.Company;
import com.renanresende.bridgetotalk.domain.CompanySettings;
import com.renanresende.bridgetotalk.domain.CompanyStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class CompanyService implements ManageCompanyUseCase {

    private final CompanyRepositoryPort repository;
    private final CompanyCommandMapper commandMapper;

    public CompanyService(CompanyRepositoryPort repository,
                          CompanyCommandMapper commandMapper) {
        this.repository = repository;
        this.commandMapper = commandMapper;
    }

    @Override
    public Company create(Company company) {
        return repository.save(company);
    }

    @Override
    public Company update(UpdateCompanyCommand command) throws BusinessException {

        var existing = repository.findById(command.id())
                .orElseThrow(() -> new BusinessException("Company not found"));

        commandMapper.updateDomainFromCommand(command, existing);

        existing.setUpdatedAt(Instant.now());

        return repository.save(existing);
    }

    @Override
    public CompanySettings updateSettings(UUID companyId, CompanySettings newSettings) throws BusinessException {

        var existingCompany = repository.findById(companyId)
                .orElseThrow(() -> new BusinessException("Company not found"));

        newSettings = CompanySettings.updatePlan(newSettings, existingCompany.getSettings());

        return repository.updateSettings(newSettings, existingCompany);
    }

    @Override
    public Company get(UUID id) {
        return repository.findById(id).orElseThrow();
    }

    @Override
    public List<Company> list() {
        return repository.findAll();
    }

    @Override
    public Company changeStatus(UUID companyId, CompanyStatus newStatus) throws BusinessException {

        Company company = repository.findById(companyId)
                .orElseThrow(() -> new BusinessException("Company not found"));

        // Validações de negócio
        validateStatusTransition(company.getStatus(), newStatus);

        repository.updateStatus(companyId, newStatus);

        // Atualiza o objeto em memória para retornar com dados atualizados
        company.changeStatus(newStatus);

        return company;
    }

    private void validateStatusTransition(CompanyStatus current, CompanyStatus target) throws BusinessException {
        if (current == target) {
            throw new BusinessException("A empresa já está com o status: " + target);
        }
    }
}

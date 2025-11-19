package com.renanresende.talkbridge.application.service;

import com.renanresende.talkbridge.application.port.in.CreateCompanyUseCase;
import com.renanresende.talkbridge.application.port.out.CompanyRepositoryPort;
import com.renanresende.talkbridge.domain.Company;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

// Anotação Spring (pertence à infraestrutura, mas colocamos aqui por conveniência)
 @Service
public class CreateCompanyService implements CreateCompanyUseCase {

    private final CompanyRepositoryPort companyRepositoryPort;

    // Injeção da Porta de Saída (Repositório)
    public CreateCompanyService(CompanyRepositoryPort companyRepositoryPort) {
        this.companyRepositoryPort = companyRepositoryPort;
    }

    @Override
    public Company createCompany(CreateCompanyCommand command) {

        // 1. Regra de Negócio: Verificar unicidade de email/slug
        if (companyRepositoryPort.existsByEmailOrSlug(command.getEmail(), command.getSlug())) {
            // Em um projeto real, lançaríamos uma exceção específica (ex: DomainException)
            throw new IllegalArgumentException("Company with given email or slug already exists.");
        }

        var newCompany = Company.builder()
                .id(UUID.randomUUID())
                .name(command.getName())
                .slug(command.getSlug())
                .email(command.getEmail())
                .plan(command.getPlan())
                .createdAt( Instant.now())
                .build();

        // 3. Executar a Persistência (Chamar a Porta de Saída)
        return companyRepositoryPort.save(newCompany);
    }
}
package com.renanresende.bridgetotalk.infrastructure.config;

import com.renanresende.bridgetotalk.adapter.in.web.dto.AgentDto;
import com.renanresende.bridgetotalk.adapter.in.web.mapper.AgentDtoMapper;
import com.renanresende.bridgetotalk.application.port.in.ManageAgentUseCase;
import com.renanresende.bridgetotalk.application.port.in.ManageCompanyUseCase;
import com.renanresende.bridgetotalk.application.port.in.command.CreateAgentCommand;
import com.renanresende.bridgetotalk.domain.*;
import com.renanresende.bridgetotalk.application.port.out.CompanyRepositoryPort;
import com.renanresende.bridgetotalk.application.port.out.AgentRepositoryPort;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.Instant;
import java.util.*;

@Configuration
@Profile({"local"})
@Slf4j
public class FakeDataSeeder {

    @Bean
    CommandLineRunner initDatabase(
            ManageCompanyUseCase manageCompanyUseCase,
            ManageAgentUseCase manageAgentUseCase
    ) {
        return args -> {
            log.info("Starting database initialization with fake data...");

            var faker = new Faker(new Locale("pt-BR"));

            // Verificar se já tem dados
            if (!manageCompanyUseCase.list().isEmpty()) {
                log.info("Database already contains data. Skipping initialization.");
                return;
            }

            // Criar empresas
            var companies = createCompanies(faker, manageCompanyUseCase);

            // Criar agentes para cada empresa
            for (Company company : companies) {
                createAgents(faker, manageAgentUseCase, company.getId());
            }

            log.info("Database initialized successfully!");
            log.info("Created {} companies with agents", companies.size());
        };
    }

    private List<Company> createCompanies(Faker faker, ManageCompanyUseCase service) {
        var companies = new ArrayList<Company>();

        // Templates de empresas variadas
        String[][] companyTemplates = {
                {"TechStart", "techstart", "Startup de tecnologia"},
                {"MegaRetail", "megaretail", "Grande rede de varejo"},
                {"HealthCare Plus", "healthcareplus", "Clínica médica"},
                {"FinanceHub", "financehub", "Fintech"},
                {"EduLearn", "edulearn", "Plataforma educacional"}
        };

        Plan[] plans = Plan.values();

        for (int i = 0; i < companyTemplates.length; i++) {

            var template = companyTemplates[i];
            var plan = plans[i % plans.length];

            var company = Company.createNew(
                    template[0],
                    template[1],
                    template[1] + template[0] + ".com",
                    faker.phoneNumber().cellPhone(),
                    faker.number().digits(14),
                    plan
            );

            var saved = service.create(company);
            companies.add(saved);

            log.info("Created company: {} (Plan: {})", saved.getName(), plan);
        }

        return companies;
    }

    private void createAgents(Faker faker, ManageAgentUseCase manageAgentUseCase, UUID companyId) {

        AgentRole[] roles = {AgentRole.ADMIN, AgentRole.MANAGER, AgentRole.AGENT, AgentRole.AGENT};
        AgentStatus[] statuses = {AgentStatus.AVAILABLE, AgentStatus.BUSY, AgentStatus.OFFLINE};

        for (int i = 0; i < 4; i++) {
            var firstName = faker.name().firstName();
            var lastName = faker.name().lastName();
            var email = firstName.toLowerCase() + "." + lastName.toLowerCase() + "@company.com";

            var createAgentCommand = new CreateAgentCommand(
                    companyId,
                    firstName + " " + lastName,
                    email,
                    "$2a$10$dummyHashForTesting", // Senha hasheada fake
                    roles[i]
            );

            var agentSaved = manageAgentUseCase.create(createAgentCommand);

           log.info("Agent created: {} with id: {}, email: {}, role: {} and status: {}",
                   agentSaved.getName(), agentSaved.getId(), agentSaved.getEmail(),
                   agentSaved.getRole(), agentSaved.getStatus());
        }
    }
}
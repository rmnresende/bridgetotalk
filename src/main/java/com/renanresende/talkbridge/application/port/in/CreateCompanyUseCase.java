package com.renanresende.talkbridge.application.port.in;

import com.renanresende.talkbridge.domain.Company;
import lombok.Getter;

import java.util.UUID;

public interface CreateCompanyUseCase {

    Company createCompany(CreateCompanyCommand command);

    // Inner Class para encapsular os dados de entrada
    @Getter
    class CreateCompanyCommand {
        private final String name;
        private final String slug;
        private final String email;
        private final String plan;

        public CreateCompanyCommand(String name, String slug, String email, String plan) {
            this.name = name;
            this.slug = slug;
            this.email = email;
            this.plan = plan;
        }
    }
}
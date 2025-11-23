package com.renanresende.bridgetotalk.application.port.in;

import com.renanresende.bridgetotalk.domain.Company;

import java.util.UUID;

public interface UpdateCompanyUseCase {
    Company updateCompany(UUID id, Company company);
}

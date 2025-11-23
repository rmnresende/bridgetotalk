package com.renanresende.bridgetotalk.application.port.in;

import com.renanresende.bridgetotalk.domain.Company;

public interface CreateCompanyUseCase {
    Company createCompany(Company company);
}

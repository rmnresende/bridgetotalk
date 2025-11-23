package com.renanresende.bridgetotalk.application.port.in;

import com.renanresende.bridgetotalk.domain.Company;

import java.util.UUID;

public interface GetCompanyUseCase {
    Company getCompany(UUID id);
}

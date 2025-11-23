package com.renanresende.bridgetotalk.application.port.in.command;

import com.renanresende.bridgetotalk.domain.CompanySettings;
import com.renanresende.bridgetotalk.domain.CompanyStatus;
import com.renanresende.bridgetotalk.domain.Plan;
import java.util.UUID;

public record UpdateCompanyCommand(
        UUID id,
        String name,
        String email,
        String phone,
        String document,
        CompanyStatus status,
        String slug,
        CompanySettings settings
) {}
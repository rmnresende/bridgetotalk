package com.renanresende.bridgetotalk.adapter.in.web.dto.company;

import com.renanresende.bridgetotalk.domain.organization.CompanyStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateCompanyStatusRequestDto(
        @NotNull(message = "Status is required")
        CompanyStatus status
){}


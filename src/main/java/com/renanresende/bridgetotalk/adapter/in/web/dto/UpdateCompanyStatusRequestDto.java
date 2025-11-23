package com.renanresende.bridgetotalk.adapter.in.web.dto;

import com.renanresende.bridgetotalk.adapter.in.web.validation.ValidEnum;
import com.renanresende.bridgetotalk.domain.CompanyStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateCompanyStatusRequestDto(
        @NotNull(message = "Status is required")
        @ValidEnum(enumClass = CompanyStatus.class, message = "Invalid status. Accepted values:  ACTIVE, SUSPENDED, INACTIVE")
        CompanyStatus status
){}


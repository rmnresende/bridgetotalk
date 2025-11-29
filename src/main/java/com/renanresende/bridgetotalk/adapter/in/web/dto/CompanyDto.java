package com.renanresende.bridgetotalk.adapter.in.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.renanresende.bridgetotalk.domain.CompanyStatus;

import java.time.Instant;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
public record CompanyDto(
        UUID id,
        String name,
        String slug,
        String email,
        String phone,
        String document,
        CompanyStatus status,
        CompanySettingsUpdateDto settings,
        Instant createdAt,
        Instant updatedAt,
        Instant deletedAt
) {}

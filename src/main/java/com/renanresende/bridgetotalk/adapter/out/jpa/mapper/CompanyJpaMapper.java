package com.renanresende.bridgetotalk.adapter.out.jpa.mapper;

import com.renanresende.bridgetotalk.adapter.out.jpa.entity.CompanyJpaEntity;
import com.renanresende.bridgetotalk.adapter.out.jpa.entity.CompanySettingsJpaEntity;
import com.renanresende.bridgetotalk.domain.CompanyStatus;
import com.renanresende.bridgetotalk.domain.Plan;
import com.renanresende.bridgetotalk.domain.exception.BusinessException;
import com.renanresende.bridgetotalk.domain.Company;
import com.renanresende.bridgetotalk.domain.CompanySettings;
import org.mapstruct.*;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface CompanyJpaMapper {

    default CompanyJpaEntity toEntity(Company company) {

        CompanySettingsJpaEntity settings = null;

        if (company.getSettings() != null) {
            settings = toSettingsEntityFromDomain(company.getSettings());
        }

        var entity = new CompanyJpaEntity(
                company.getId(),
                company.getName(),
                company.getSlug(),
                company.getEmail(),
                company.getDocument(),
                company.getPhone(),
                company.getStatus(),
                company.getCreatedAt(),
                company.getUpdatedAt(),
                company.getDeletedAt(),
                settings
        );

        if (settings != null) {
            settings.setCompany(entity);
        }

        return entity;
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDomain(
            Company domain,
            @MappingTarget CompanyJpaEntity entity
    );

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateSettingsEntityFromDomain(
            CompanySettings domain,
            @MappingTarget CompanySettingsJpaEntity entity
    );

    default Company toDomain(CompanyJpaEntity entity) {

        var settings = entity.getSettings() != null
                ? companySettingsJpaEntityToCompanySettings(entity.getSettings())
                : null;

        return Company.rehydrate(
                entity.getId(),
                entity.getName(),
                entity.getSlug(),
                entity.getEmail(),
                entity.getPhone(),
                entity.getDocument(),
                entity.getStatus(),
                settings,
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getDeletedAt()
        );
    }

    CompanySettingsJpaEntity toSettingsEntityFromDomain(CompanySettings domain);

    CompanySettings companySettingsJpaEntityToCompanySettings(
            CompanySettingsJpaEntity entity
    );
}

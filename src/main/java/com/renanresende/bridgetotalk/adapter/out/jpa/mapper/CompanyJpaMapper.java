package com.renanresende.bridgetotalk.adapter.out.jpa.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.renanresende.bridgetotalk.adapter.out.jpa.entity.CompanyJpaEntity;
import com.renanresende.bridgetotalk.adapter.out.jpa.entity.CompanySettingsJpaEntity;
import com.renanresende.bridgetotalk.commom.BusinessException;
import com.renanresende.bridgetotalk.domain.Company;
import com.renanresende.bridgetotalk.domain.CompanySettings;
import com.renanresende.bridgetotalk.domain.Plan;
import lombok.RequiredArgsConstructor;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Mapper(componentModel = "spring")
public interface CompanyJpaMapper {

    Company toDomain(CompanyJpaEntity entity);

    default CompanyJpaEntity toEntity(Company company) {
        if ( company == null ) {
            return null;
        }

        CompanyJpaEntity.CompanyJpaEntityBuilder companyJpaEntity = CompanyJpaEntity.builder();

        companyJpaEntity.id( company.getId() );
        companyJpaEntity.name( company.getName() );
        companyJpaEntity.slug( company.getSlug() );
        companyJpaEntity.email( company.getEmail() );
        companyJpaEntity.document( company.getDocument() );
        companyJpaEntity.phone( company.getPhone() );
        companyJpaEntity.status( company.getStatus() );
        companyJpaEntity.createdAt( company.getCreatedAt() );
        companyJpaEntity.updatedAt( company.getUpdatedAt() );
        companyJpaEntity.deletedAt( company.getDeletedAt() );
        companyJpaEntity.settings( toSettingsEntity( company.getSettings() ) );

        var entity = companyJpaEntity.build();
        linkBidirectionalReferences(company, entity);
        return entity;
    }

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "id", ignore = true)// Geralmente não muda
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDomain(Company domain, @MappingTarget CompanyJpaEntity entity);

    CompanySettingsJpaEntity toSettingsEntity(CompanySettings domainCompanySettings);

    default CompanySettingsJpaEntity toSettingsEntity(CompanySettings domainCompanySettings, Company company) {
      var companySettingsJpaEntity  = toSettingsEntity(domainCompanySettings);
      companySettingsJpaEntity.setCompanyId(company.getId());
      return companySettingsJpaEntity;
    }

    @AfterMapping
    default void linkBidirectionalReferences(Company company, @MappingTarget CompanyJpaEntity entity) {
        if (entity.getSettings() != null) {
            entity.getSettings().setCompany(entity);
            entity.getSettings().setCompanyId(entity.getId());
        }
    }

    CompanySettings companySettingsJpaEntityToCompanySettings(CompanySettingsJpaEntity companySettingsJpaEntity) throws BusinessException;
}

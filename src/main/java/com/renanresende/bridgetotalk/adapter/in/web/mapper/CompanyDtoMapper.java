package com.renanresende.bridgetotalk.adapter.in.web.mapper;

import com.renanresende.bridgetotalk.adapter.in.web.dto.CompanyDto;
import com.renanresende.bridgetotalk.adapter.in.web.dto.CompanySettingsDto;
import com.renanresende.bridgetotalk.adapter.out.jpa.entity.CompanyJpaEntity;
import com.renanresende.bridgetotalk.application.port.in.command.UpdateCompanyCommand;
import com.renanresende.bridgetotalk.commom.BusinessException;
import com.renanresende.bridgetotalk.domain.Company;
import com.renanresende.bridgetotalk.domain.CompanySettings;
import com.renanresende.bridgetotalk.domain.Plan;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.time.Instant;
import java.util.UUID;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface CompanyDtoMapper {

    @Mapping(target = "id", ignore = true) // sempre gerado pelo Company()
    Company toDomain(CompanyDto dto);

    CompanyDto toDto(Company company);

    @Mapping(target = "id", source = "id")
    UpdateCompanyCommand toCommand(UUID id, CompanyDto dto);

    default CompanySettings companySettingsDtoToCompanySettings(CompanySettingsDto companySettingsDto) throws BusinessException {
        if ( companySettingsDto == null ) {
            return null;
        }

        return CompanySettings.createFromPlan(companySettingsDto.plan());
    }
}

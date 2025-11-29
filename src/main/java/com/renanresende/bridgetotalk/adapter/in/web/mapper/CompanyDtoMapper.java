package com.renanresende.bridgetotalk.adapter.in.web.mapper;

import com.renanresende.bridgetotalk.adapter.in.web.dto.CompanyDto;
import com.renanresende.bridgetotalk.adapter.in.web.dto.CompanySettingsUpdateDto;
import com.renanresende.bridgetotalk.application.port.in.command.UpdateCompanyCommand;
import com.renanresende.bridgetotalk.domain.exception.BusinessException;
import com.renanresende.bridgetotalk.domain.Company;
import com.renanresende.bridgetotalk.domain.CompanySettings;
import org.mapstruct.*;

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

    default CompanySettings companySettingsUpdateDtoToCompanySettings(CompanySettingsUpdateDto companySettingsDto) throws BusinessException {
        if ( companySettingsDto == null ) {
            return null;
        }

        return CompanySettings.createFromPlan(companySettingsDto.plan());
    }
}

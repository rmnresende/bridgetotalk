package com.renanresende.bridgetotalk.adapter.in.web.mapper;

import com.renanresende.bridgetotalk.adapter.in.web.dto.CreateCompanyDto;
import com.renanresende.bridgetotalk.adapter.in.web.dto.CompanySettingsUpdateDto;
import com.renanresende.bridgetotalk.adapter.in.web.dto.ResponseCompanyDto;
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
    default Company toDomain(CreateCompanyDto dto){

        return Company.createNew(
                dto.name(),
                dto.slug(),
                dto.email(),
                dto.phone(),
                dto.document(),
                dto.plan()
        );
    }

    CreateCompanyDto toCreateCompanyDto(Company company);

    @Mapping(target = "id", source = "id")
    UpdateCompanyCommand toCommand(UUID id, CreateCompanyDto dto);

    ResponseCompanyDto toResponseDto(Company company);

    default CompanySettings companySettingsUpdateDtoToCompanySettings(CompanySettingsUpdateDto companySettingsDto) throws BusinessException {
        if ( companySettingsDto == null ) {
            return null;
        }

        return CompanySettings.createFromPlan(companySettingsDto.plan());
    }
}

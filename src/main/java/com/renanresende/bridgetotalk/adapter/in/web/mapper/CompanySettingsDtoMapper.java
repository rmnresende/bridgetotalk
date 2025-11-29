package com.renanresende.bridgetotalk.adapter.in.web.mapper;

import com.renanresende.bridgetotalk.adapter.in.web.dto.CompanySettingsUpdateDto;
import com.renanresende.bridgetotalk.application.port.in.command.UpdateCompanySettingsCommand;
import com.renanresende.bridgetotalk.domain.exception.BusinessException;
import com.renanresende.bridgetotalk.domain.CompanySettings;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface CompanySettingsDtoMapper {

    CompanySettingsUpdateDto toDto(CompanySettings companySettings);

    UpdateCompanySettingsCommand toCommand(CompanySettingsUpdateDto companySettingsUpdateDTO);

    default CompanySettings toDomain(CompanySettingsUpdateDto companySettingsUpdateDTO) throws BusinessException {
        if (companySettingsUpdateDTO == null) {
            return null;
        }

        CompanySettings companySettingsFromPlan = null;

        if (companySettingsUpdateDTO.plan() != null) {

            // gera o domain com informacoes default do plano
            companySettingsFromPlan = CompanySettings.createFromPlan(companySettingsUpdateDTO.plan());

            if (companySettingsUpdateDTO.maxAgents() != null) {
                companySettingsFromPlan.setMaxAgents(companySettingsUpdateDTO.maxAgents());
            }

            if (companySettingsUpdateDTO.maxQueues() != null) {
                companySettingsFromPlan.setMaxQueues(companySettingsUpdateDTO.maxQueues());
            }

            if (companySettingsUpdateDTO.timezone() != null) {
                companySettingsFromPlan.setTimezone(companySettingsUpdateDTO.timezone());
            }

            if (companySettingsUpdateDTO.language() != null) {
                companySettingsFromPlan.setLanguage(companySettingsUpdateDTO.language());
            }

            return companySettingsFromPlan;
        }

        return new CompanySettings(companySettingsUpdateDTO.maxAgents(),
                companySettingsUpdateDTO.maxQueues(),
                companySettingsUpdateDTO.timezone(),
                companySettingsUpdateDTO.language(),
                null,
                null);

    }
}

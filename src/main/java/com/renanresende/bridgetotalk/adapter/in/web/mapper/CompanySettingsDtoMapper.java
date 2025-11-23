package com.renanresende.bridgetotalk.adapter.in.web.mapper;

import com.renanresende.bridgetotalk.adapter.in.web.dto.CompanySettingsDto;
import com.renanresende.bridgetotalk.commom.BusinessException;
import com.renanresende.bridgetotalk.domain.CompanySettings;
import com.renanresende.bridgetotalk.domain.Plan;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.time.Instant;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface CompanySettingsDtoMapper {

    CompanySettingsDto toDto(CompanySettings companySettings);

    default CompanySettings toDomain(CompanySettingsDto dto) throws BusinessException {
        if (dto == null) {
            return null;
        }

        CompanySettings companySettingsFromPlan = null;

        if (dto.plan() != null) {

            // gera o domain com informacoes default do plano
            companySettingsFromPlan = CompanySettings.createFromPlan(dto.plan());

            if (dto.maxAgents() != null) {
                companySettingsFromPlan.setMaxAgents(dto.maxAgents());
            }

            if (dto.maxQueues() != null) {
                companySettingsFromPlan.setMaxQueues(dto.maxQueues());
            }

            if (dto.timezone() != null) {
                companySettingsFromPlan.setTimezone(dto.timezone());
            }

            if (dto.language() != null) {
                companySettingsFromPlan.setLanguage(dto.language());
            }

            return companySettingsFromPlan;
        }

        return new CompanySettings(dto.maxAgents(),
                dto.maxQueues(),
                dto.timezone(),
                dto.language(),
                null,
                null);

    }
}

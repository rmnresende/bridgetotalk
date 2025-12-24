package com.renanresende.bridgetotalk.adapter.in.web.mapper;

import com.renanresende.bridgetotalk.adapter.in.web.dto.company.CompanySettingsUpdateDto;
import com.renanresende.bridgetotalk.application.port.in.command.UpdateCompanySettingsCommand;
import com.renanresende.bridgetotalk.domain.organization.CompanySettings;
import com.renanresende.bridgetotalk.domain.shared.exception.BusinessException;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.Objects;

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

        //default values from plan
        if (companySettingsUpdateDTO.plan() != null) {
            companySettingsFromPlan = CompanySettings.createFromPlan(companySettingsUpdateDTO.plan());
        }

        //custom values
        var maxAgents = Objects.isNull(companySettingsUpdateDTO.maxAgents()) ?
                companySettingsFromPlan.getMaxAgents() : companySettingsUpdateDTO.maxAgents();

        var maxQueues = Objects.isNull(companySettingsUpdateDTO.maxQueues()) ?
                companySettingsFromPlan.getMaxQueues() : companySettingsUpdateDTO.maxQueues();

        var timezone = Objects.isNull(companySettingsUpdateDTO.timezone()) ?
                companySettingsFromPlan.getTimezone() : companySettingsUpdateDTO.timezone();

        var language = Objects.isNull(companySettingsUpdateDTO.language()) ?
                companySettingsFromPlan.getLanguage() : companySettingsUpdateDTO.language();

        var maxConcurrentConversationsPerAgent = Objects.isNull(companySettingsUpdateDTO.maxConcurrentConversationsPerAgent()) ?
                companySettingsFromPlan.getMaxConcurrentConversationsPerAgent() : companySettingsUpdateDTO.maxConcurrentConversationsPerAgent();

        return new CompanySettings(maxAgents,
                                   maxQueues,
                                   timezone,
                                   language,
                          null,
                              null,
                                   maxConcurrentConversationsPerAgent
        );
    }
}

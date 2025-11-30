package com.renanresende.bridgetotalk.adapter.in.web.mapper;

import com.renanresende.bridgetotalk.adapter.in.web.dto.AgentDto;
import com.renanresende.bridgetotalk.adapter.in.web.dto.CompanyDto;
import com.renanresende.bridgetotalk.application.port.in.command.CreateAgentCommand;
import com.renanresende.bridgetotalk.domain.Agent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.UUID;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface AgentDtoMapper {

    @Mapping(target = "id", ignore = true)
    CreateAgentCommand toCommand(AgentDto dto);

    AgentDto toDto(Agent agent);
}

package com.renanresende.bridgetotalk.application.mapper;

import com.renanresende.bridgetotalk.adapter.out.jpa.entity.AgentJpaEntity;
import com.renanresende.bridgetotalk.application.port.in.command.CreateAgentCommand;
import com.renanresende.bridgetotalk.domain.people.Agent;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface AgentCommandMapper {

    AgentJpaEntity toEntity(CreateAgentCommand command);

    default Agent toDomain(CreateAgentCommand command){
        return Agent.createNew(
                command.companyId(),
                command.name(),
                command.email(),
                command.passwordHash(),
                command.role());
    }
}

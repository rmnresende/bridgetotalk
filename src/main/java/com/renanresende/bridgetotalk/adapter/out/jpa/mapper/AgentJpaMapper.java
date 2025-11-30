package com.renanresende.bridgetotalk.adapter.out.jpa.mapper;

import com.renanresende.bridgetotalk.adapter.out.jpa.entity.AgentJpaEntity;
import com.renanresende.bridgetotalk.domain.Agent;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AgentJpaMapper {

    default Agent toDomain(AgentJpaEntity agentJpaEntity){
        return Agent.createNew(agentJpaEntity.getId(),
                agentJpaEntity.getCompanyId(),
                agentJpaEntity.getName(),
                agentJpaEntity.getEmail(),
                agentJpaEntity.getPasswordHash(),
                agentJpaEntity.getRole());
    }

    AgentJpaEntity toEntity(Agent agent);
}

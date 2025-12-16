package com.renanresende.bridgetotalk.adapter.out.jpa.mapper;

import com.renanresende.bridgetotalk.adapter.out.jpa.entity.AgentJpaEntity;
import com.renanresende.bridgetotalk.domain.Agent;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AgentJpaMapper {

    default Agent toDomain(AgentJpaEntity entity) {
        if (entity == null) return null;

        return Agent.rehydrate(
                entity.getId(),
                entity.getCompanyId(),
                entity.getName(),
                entity.getEmail(),
                entity.getPasswordHash(),
                entity.getRole(),
                entity.getStatus(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getDeletedAt()
        );
    }


    AgentJpaEntity toEntity(Agent agent);
}

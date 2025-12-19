package com.renanresende.bridgetotalk.adapter.out.jpa.mapper;

import com.renanresende.bridgetotalk.adapter.out.jpa.entity.QueueJpaEntity;
import com.renanresende.bridgetotalk.domain.attendance.Queue;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QueueJpaMapper {

    default Queue toDomain(QueueJpaEntity entity){

        return Queue.rehydrate(
                entity.getId(),
                entity.getCompanyId(),
                entity.getName(),
                entity.getDistributionStrategy(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getDeletedAt());
    }

    QueueJpaEntity toEntity(Queue queue);
}

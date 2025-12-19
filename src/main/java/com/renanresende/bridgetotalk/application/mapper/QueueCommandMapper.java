package com.renanresende.bridgetotalk.application.mapper;

import com.renanresende.bridgetotalk.adapter.out.jpa.entity.QueueJpaEntity;
import com.renanresende.bridgetotalk.application.port.in.command.CreateQueueCommand;
import com.renanresende.bridgetotalk.application.port.in.command.UpdateQueueCommand;
import com.renanresende.bridgetotalk.domain.attendance.Queue;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.UUID;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface QueueCommandMapper {

    QueueJpaEntity toEntity(CreateQueueCommand command);

    default Queue fromCreateCommandtoDomain(CreateQueueCommand createQueueCommand){

        return Queue.createNew(
                createQueueCommand.companyId(),
                createQueueCommand.name(),
                createQueueCommand.distributionStrategy());
    }

    default Queue fromUpdateCommandtoDomain(UUID id, UpdateQueueCommand updateQueueCommand){

        return Queue.hydrate(
                id,
                updateQueueCommand.companyId(),
                updateQueueCommand.name(),
                updateQueueCommand.distributionStrategy()
        );
    }
}

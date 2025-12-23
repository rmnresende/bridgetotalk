package com.renanresende.bridgetotalk.application.mapper;

import com.renanresende.bridgetotalk.adapter.out.jpa.entity.QueueJpaEntity;
import com.renanresende.bridgetotalk.adapter.out.jpa.entity.QueueSettingsJpaEntity;
import com.renanresende.bridgetotalk.application.port.in.command.CreateQueueCommand;
import com.renanresende.bridgetotalk.application.port.in.command.UpdateQueueCommand;
import com.renanresende.bridgetotalk.domain.attendance.Queue;
import com.renanresende.bridgetotalk.domain.attendance.QueueSettings;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.Objects;
import java.util.UUID;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface QueueCommandMapper {

    default QueueJpaEntity toEntity(CreateQueueCommand command){

        QueueSettingsJpaEntity queueSettingsJpaEntity = null;
        QueueSettings commandSettings = command.settings();

        if (Objects.isNull(commandSettings)) {
            commandSettings = QueueSettings.initializeWithDefaultSettings();
        }

        queueSettingsJpaEntity = QueueSettingsJpaEntity.builder()
                                                       .maxWaitingTime(command.settings().getMaxWaitingTime())
                                                       .autoCloseAfterInactivity(command.settings().getAutoCloseAfterInactivity())
                                                       .welcomeMessage(command.settings().getWelcomeMessage().content())
                                                       .welcomeEnabled(command.settings().getWelcomeMessage().enabled())
                                                       .offHoursMessage(command.settings().getOffHoursMessage().content())
                                                       .offHoursEnabled(command.settings().getOffHoursMessage().enabled())
                                                       .waitingMessage(command.settings().getWaitingMessage().content())
                                                       .waitingEnabled(command.settings().getWaitingMessage().enabled())
                                                       .build();

        return QueueJpaEntity.builder()
                .distributionStrategy(command.distributionStrategy())
                .name(command.name())
                .settings(queueSettingsJpaEntity)
                .build();
    }

    default Queue fromCreateCommandtoDomain(CreateQueueCommand createQueueCommand){
        return Queue.createNew(
                createQueueCommand.companyId(),
                createQueueCommand.name(),
                createQueueCommand.distributionStrategy(),
                createQueueCommand.settings());
    }

    default Queue fromUpdateCommandtoDomain(UUID id, UpdateQueueCommand updateQueueCommand){
        return Queue.hydrate(id,
                             updateQueueCommand.companyId(),
                             updateQueueCommand.name(),
                             updateQueueCommand.distributionStrategy(),
                             updateQueueCommand.settings()
        );
    }
}

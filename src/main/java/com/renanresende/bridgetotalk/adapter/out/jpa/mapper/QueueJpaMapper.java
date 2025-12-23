package com.renanresende.bridgetotalk.adapter.out.jpa.mapper;

import com.renanresende.bridgetotalk.adapter.out.jpa.entity.QueueJpaEntity;
import com.renanresende.bridgetotalk.adapter.out.jpa.entity.QueueSettingsJpaEntity;
import com.renanresende.bridgetotalk.domain.attendance.AutomatedMessage;
import com.renanresende.bridgetotalk.domain.attendance.Queue;
import com.renanresende.bridgetotalk.domain.attendance.QueueSettings;
import org.mapstruct.Mapper;

import java.util.Objects;

@Mapper(componentModel = "spring")
public interface QueueJpaMapper {

    default Queue toDomain(QueueJpaEntity entity){

        QueueSettings domainSettings = null;

        if (Objects.isNull(entity.getSettings())) {
            domainSettings = QueueSettings.initializeWithDefaultSettings();
        }else {
            domainSettings = queueSettingsJpaEntityToQueueSettingsDomain(entity.getSettings());
        }

        return Queue.rehydrate(
                entity.getId(),
                entity.getCompanyId(),
                entity.getName(),
                entity.getDistributionStrategy(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getDeletedAt(),
                domainSettings);
    }

    default QueueJpaEntity toEntity(Queue queue) {
        if ( queue == null ) {
            return null;
        }

        var queueJpaEntity = new QueueJpaEntity();

        queueJpaEntity.setId( queue.getId() );
        queueJpaEntity.setName( queue.getName() );
        queueJpaEntity.setCompanyId( queue.getCompanyId() );
        queueJpaEntity.setDistributionStrategy( queue.getDistributionStrategy() );
        queueJpaEntity.setCreatedAt( queue.getCreatedAt() );
        queueJpaEntity.setUpdatedAt( queue.getUpdatedAt() );
        queueJpaEntity.setDeletedAt( queue.getDeletedAt() );
        queueJpaEntity.setSettings( queueSettingsToQueueSettingsJpaEntity(queueJpaEntity, queue.getSettings() ) );

        return queueJpaEntity;
    }

    private QueueSettingsJpaEntity queueSettingsToQueueSettingsJpaEntity(QueueJpaEntity queueJpaEntity, QueueSettings queueSettings) {
        if ( queueSettings == null ) {
            queueSettings = QueueSettings.initializeWithDefaultSettings();
        }

        var queueSettingsJpaEntity = new QueueSettingsJpaEntity();
        queueSettingsJpaEntity.setQueueId(queueJpaEntity.getId());
        queueSettingsJpaEntity.setQueue(queueJpaEntity);
        queueSettingsJpaEntity.setWaitingMessage(queueSettings.getWaitingMessage().content());
        queueSettingsJpaEntity.setWaitingEnabled(queueSettings.getWaitingMessage().enabled());
        queueSettingsJpaEntity.setOffHoursMessage(queueSettings.getOffHoursMessage().content());
        queueSettingsJpaEntity.setOffHoursEnabled(queueSettings.getOffHoursMessage().enabled());
        queueSettingsJpaEntity.setWelcomeMessage(queueSettings.getWelcomeMessage().content());
        queueSettingsJpaEntity.setWelcomeEnabled(queueSettings.getWelcomeMessage().enabled());

        var weeklySchedule = WeeklyScheduleJsonMapper.toJson(queueSettings.getWeeklySchedule());
        queueSettingsJpaEntity.setWeeklySchedule(queueSettings.getWeeklySchedule());
        return queueSettingsJpaEntity;
    }

    private QueueSettings queueSettingsJpaEntityToQueueSettingsDomain(QueueSettingsJpaEntity queueSettingsJpaEntity){

        if(Objects.isNull(queueSettingsJpaEntity)){
            return QueueSettings.initializeWithDefaultSettings();
        }

        var welcomeMessage = new AutomatedMessage(queueSettingsJpaEntity.getWelcomeMessage(), queueSettingsJpaEntity.isWelcomeEnabled());
        var offHoursMessage = new AutomatedMessage(queueSettingsJpaEntity.getOffHoursMessage(), queueSettingsJpaEntity.isOffHoursEnabled());
        var waitingMessage = new AutomatedMessage(queueSettingsJpaEntity.getWaitingMessage(), queueSettingsJpaEntity.isWaitingEnabled());

        return QueueSettings.rehydrate( welcomeMessage,
                                        offHoursMessage,
                                        waitingMessage,
                                        queueSettingsJpaEntity.getWeeklySchedule(),
                                        queueSettingsJpaEntity.getAutoCloseAfterInactivity(),
                                        queueSettingsJpaEntity.getMaxWaitingTime()
        );
    }

}

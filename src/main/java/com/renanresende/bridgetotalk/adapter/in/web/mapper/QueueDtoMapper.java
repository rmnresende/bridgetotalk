package com.renanresende.bridgetotalk.adapter.in.web.mapper;

import com.renanresende.bridgetotalk.adapter.in.web.dto.queue.CreateQueueDto;
import com.renanresende.bridgetotalk.adapter.in.web.dto.queue.ResponseQueueDto;
import com.renanresende.bridgetotalk.adapter.in.web.dto.queue.UpdateQueueDto;
import com.renanresende.bridgetotalk.application.port.in.command.CreateQueueCommand;
import com.renanresende.bridgetotalk.application.port.in.command.UpdateQueueCommand;
import com.renanresende.bridgetotalk.domain.attendance.*;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface QueueDtoMapper {

    default CreateQueueCommand toCreateCommand(CreateQueueDto dto){

        QueueSettings queueSettings = null;

        if(Objects.isNull(dto.settings())){
            queueSettings = QueueSettings.initializeWithDefaultSettings();
        }else{
            var dtoSettings = dto.settings();

            var dailyScheduleMap = new HashMap<DayOfWeek, DailySchedule>();
            dtoSettings.weeklySchedule()
                       .schedules()
                       .forEach((dayOfWeek, dailySchedule) -> {

                            var domainTimeRange = new ArrayList<TimeRange>();
                            dailySchedule.ranges().forEach(range -> {
                                domainTimeRange.add(new TimeRange(range.start(), range.end()));
                            });

                            dailyScheduleMap.put(dayOfWeek, new DailySchedule(domainTimeRange));
                    });

            var weeklySchedule = WeeklySchedule.of(dailyScheduleMap);

            queueSettings = QueueSettings.rehydrate(new AutomatedMessage(dtoSettings.welcomeMessage().content(), dtoSettings.welcomeMessage().enabled()),
                                                    new AutomatedMessage(dtoSettings.welcomeMessage().content(), dtoSettings.welcomeMessage().enabled()),
                                                    new AutomatedMessage(dtoSettings.welcomeMessage().content(), dtoSettings.welcomeMessage().enabled()),
                                                    weeklySchedule,
                                                    dto.settings().autoCloseAfterInactivity(),
                                                    dto.settings().maxWaitingTime()
            );
        }

        return new CreateQueueCommand(
                dto.companyId(),
                dto.name(),
                dto.distributionStrategy(),
                queueSettings
        );
    }

    UpdateQueueCommand toUpdateCommand(UUID id, UpdateQueueDto dto);

    ResponseQueueDto toResponseDto(Queue queue);
}

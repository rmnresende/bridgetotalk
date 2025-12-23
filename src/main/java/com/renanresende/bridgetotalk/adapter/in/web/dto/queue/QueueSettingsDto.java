package com.renanresende.bridgetotalk.adapter.in.web.dto.queue;

import java.time.Duration;

public record QueueSettingsDto(
         AutomatedMessageDto welcomeMessage,
         AutomatedMessageDto offHoursMessage,
         AutomatedMessageDto waitingMessage,
         WeeklyScheduleDto weeklySchedule,
         Duration autoCloseAfterInactivity,
         Duration maxWaitingTime
) {}

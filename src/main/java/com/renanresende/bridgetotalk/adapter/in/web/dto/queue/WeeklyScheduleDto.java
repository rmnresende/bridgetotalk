package com.renanresende.bridgetotalk.adapter.in.web.dto.queue;

import java.time.DayOfWeek;
import java.util.Map;

public record WeeklyScheduleDto(
        Map<DayOfWeek, DailyScheduleDto> schedules
) {
}

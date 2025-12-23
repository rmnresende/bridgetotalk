package com.renanresende.bridgetotalk.adapter.out.jpa.dto;

import java.time.DayOfWeek;
import java.util.Map;

public record WeeklyScheduleJson(
        Map<DayOfWeek, DailyScheduleJson> schedules
) {}


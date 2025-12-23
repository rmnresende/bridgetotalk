package com.renanresende.bridgetotalk.adapter.out.jpa.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.renanresende.bridgetotalk.adapter.out.jpa.dto.DailyScheduleJson;
import com.renanresende.bridgetotalk.adapter.out.jpa.dto.TimeRangeJson;
import com.renanresende.bridgetotalk.adapter.out.jpa.dto.WeeklyScheduleJson;
import com.renanresende.bridgetotalk.domain.attendance.DailySchedule;
import com.renanresende.bridgetotalk.domain.attendance.TimeRange;
import com.renanresende.bridgetotalk.domain.attendance.WeeklySchedule;

import java.time.DayOfWeek;
import java.util.Map;
import java.util.stream.Collectors;

public class WeeklyScheduleJsonMapper {

    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.findAndRegisterModules();
    }

    public static String toJson(WeeklySchedule schedule) {
        try {
            var dto = new WeeklyScheduleJson(
                    schedule.toMap().entrySet().stream()
                            .collect(Collectors.toMap(
                                    Map.Entry::getKey,
                                    entry -> toDailyJson(entry.getValue())
                            ))
            );
            return mapper.writeValueAsString(dto);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to serialize WeeklySchedule", e);
        }
    }

    public static WeeklySchedule fromJson(String json) {
        try {
            var dto = mapper.readValue(json, WeeklyScheduleJson.class);

            Map<DayOfWeek, DailySchedule> schedules =
                    dto.schedules().entrySet().stream()
                            .collect(Collectors.toMap(
                                    Map.Entry::getKey,
                                    entry -> fromDailyJson(entry.getValue())
                            ));

            return WeeklySchedule.of(schedules);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to deserialize WeeklySchedule", e);
        }
    }

    private static DailyScheduleJson toDailyJson(DailySchedule daily) {
        return new DailyScheduleJson(
                daily.getRanges()
                        .stream()
                        .map(r -> new TimeRangeJson(r.start(), r.end()))
                        .toList()
        );
    }

    private static DailySchedule fromDailyJson(DailyScheduleJson json) {
        return new DailySchedule(
                json.ranges().stream()
                        .map(r -> new TimeRange(r.start(), r.end()))
                        .toList()
        );
    }
}



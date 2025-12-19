package com.renanresende.bridgetotalk.domain.attendance;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class WeeklySchedule {

    private Map<DayOfWeek, DailySchedule> schedules;

    public WeeklySchedule() {}

    private WeeklySchedule(Map<DayOfWeek, DailySchedule> schedules) {
        this.schedules = schedules;
    }

    /**
     * Initializes a standard business schedule (Monday to Friday)
     * using the same time range for each day.
     */
    public static WeeklySchedule standardBusinessHours(
            LocalTime start,
            LocalTime end
    ) {

        var timeRange = new TimeRange(start, end); // validate if start is before end
        var dailySchedule = new DailySchedule(List.of(timeRange));

        Map<DayOfWeek, DailySchedule> schedules = new EnumMap<>(DayOfWeek.class);

        schedules.put(DayOfWeek.MONDAY, dailySchedule);
        schedules.put(DayOfWeek.TUESDAY, dailySchedule);
        schedules.put(DayOfWeek.WEDNESDAY, dailySchedule);
        schedules.put(DayOfWeek.THURSDAY, dailySchedule);
        schedules.put(DayOfWeek.FRIDAY, dailySchedule);

        return new WeeklySchedule(schedules);
    }

    public static WeeklySchedule closedAllWeek() {
        return new WeeklySchedule(Map.of());
    }

    public static WeeklySchedule of(Map<DayOfWeek, DailySchedule> schedules) {
        return new WeeklySchedule(Map.copyOf(schedules));
    }

    /**
     * A day without ranges, is a closed day. Return true if the time of the day,
     * is contained within any of the daily ranges.
     * @param dateTime
     * @return
     */
    public boolean isOpenAt(QueueDateTime dateTime) {
        var daily = schedules.get(dateTime.dayOfWeek());
        return daily != null && daily.isWithin(dateTime.time());
    }
}

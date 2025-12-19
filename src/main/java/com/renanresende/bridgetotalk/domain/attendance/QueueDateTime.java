package com.renanresende.bridgetotalk.domain.attendance;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record QueueDateTime(
        DayOfWeek dayOfWeek,
        LocalTime time
) {

    public static QueueDateTime now(LocalDateTime dateTime) {
        return new QueueDateTime(
                dateTime.getDayOfWeek(),
                dateTime.toLocalTime()
        );
    }
}

package com.renanresende.bridgetotalk.domain.attendance;

import java.time.LocalTime;

public record TimeRange(
        LocalTime start,
        LocalTime end
) {

    public TimeRange {
        if (start == null || end == null || !start.isBefore(end)) {
            throw new IllegalArgumentException("Start time must be before end time");
        }
    }


    /**
     * Start inclusive and end exclusive
     * @param time
     * @return
     */
    public boolean contains(LocalTime time) {
        return !time.isBefore(start) && time.isBefore(end);
    }
}

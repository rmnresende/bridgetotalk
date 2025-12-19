package com.renanresende.bridgetotalk.domain.attendance;

import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;

public class DailySchedule {

    private final List<TimeRange> ranges;

    public DailySchedule(List<TimeRange> ranges) {

        if (ranges == null || ranges.isEmpty()) {
            throw new IllegalArgumentException("DailySchedule must have at least one time range");
        }

        var sorted = ranges.stream()
                           .sorted(Comparator.comparing(TimeRange::start))
                           .toList();

        validateNoOverlap(sorted);

        this.ranges = List.copyOf(ranges);
    }

    private void validateNoOverlap(List<TimeRange> ranges) {

        for (int i = 0; i < ranges.size() - 1; i++) {

            var current = ranges.get(i);
            var next = ranges.get(i + 1);

            if (!current.end().isBefore(next.start())) {
                throw new IllegalArgumentException("Time ranges must not overlap");
            }
        }
    }

    /**
     * Return true if time containded within in any of daily ranges,
     * otherwise return false
     * @param time
     * @return
     */
    public boolean isWithin(LocalTime time) {
        return ranges.stream().anyMatch(r -> r.contains(time));
    }
}

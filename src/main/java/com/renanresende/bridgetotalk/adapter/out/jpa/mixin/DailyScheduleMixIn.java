package com.renanresende.bridgetotalk.adapter.out.jpa.mixin;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.renanresende.bridgetotalk.domain.attendance.TimeRange;

import java.util.List;

public interface DailyScheduleMixIn {
    @JsonCreator
    void DailySchedule(@JsonProperty("ranges") List<TimeRange> ranges);
}
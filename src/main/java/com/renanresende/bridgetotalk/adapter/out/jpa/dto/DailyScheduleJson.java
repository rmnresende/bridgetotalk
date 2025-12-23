package com.renanresende.bridgetotalk.adapter.out.jpa.dto;

import java.util.List;

public record DailyScheduleJson(
        List<TimeRangeJson> ranges
) {}

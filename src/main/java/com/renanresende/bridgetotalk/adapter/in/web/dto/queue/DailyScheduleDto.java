package com.renanresende.bridgetotalk.adapter.in.web.dto.queue;

import java.util.List;

public record DailyScheduleDto(
        List<TimeRangeDto> ranges
) {}

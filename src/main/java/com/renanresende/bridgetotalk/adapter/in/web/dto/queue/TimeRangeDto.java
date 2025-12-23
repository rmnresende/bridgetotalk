package com.renanresende.bridgetotalk.adapter.in.web.dto.queue;

import java.time.LocalTime;

public record TimeRangeDto(
        LocalTime start,
        LocalTime end
) {
}

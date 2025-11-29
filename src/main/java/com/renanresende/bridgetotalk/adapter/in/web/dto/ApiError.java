package com.renanresende.bridgetotalk.adapter.in.web.dto;

import java.time.Instant;

public record ApiError(
        int status,
        String error,
        String message,
        String path,
        Instant timestamp
) {}

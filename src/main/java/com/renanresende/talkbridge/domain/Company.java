package com.renanresende.talkbridge.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@Builder
public class Company {

    private final UUID id;
    private String name;
    private String slug;
    private String email;
    private String status;
    private String plan;
    private final Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;

    public Company(UUID id, Instant createdAt) {
        this.id = id;
        this.createdAt = createdAt;
    }
}
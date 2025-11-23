package com.renanresende.bridgetotalk.domain;

import java.time.Instant;
import java.util.UUID;

public class Customer {

    private final UUID id;
    private final UUID companyId;
    private String name;
    private String email;
    private String phone;
    private String channelIdentifier;
    private String status;
    private final Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;

    public Customer(UUID id, UUID companyId, Instant createdAt) {
        this.id = id;
        this.companyId = companyId;
        this.createdAt = createdAt;
    }
}
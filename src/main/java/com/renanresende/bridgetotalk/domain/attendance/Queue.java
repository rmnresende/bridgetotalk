package com.renanresende.bridgetotalk.domain.attendance;

import com.renanresende.bridgetotalk.domain.shared.exception.BusinessException;
import com.renanresende.bridgetotalk.domain.util.DomainStrings;
import lombok.Getter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
public class Queue {

    private UUID id;
    private UUID companyId;
    private String name;
    private DistributionStrategy distributionStrategy;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;
    private QueueSettings settings;

    private Queue(  UUID id,
                    UUID companyId,
                    String name,
                    DistributionStrategy distributionStrategy,
                    Instant createdAt,
                    Instant updatedAt,
                    Instant deletedAt) {
        this.id = id;
        this.companyId = companyId;
        this.name = name;
        this.distributionStrategy = distributionStrategy;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public static Queue createNew(
                 UUID companyId,
                 String name,
                 DistributionStrategy distributionStrategy) {

        if (DomainStrings.isBlank(name)){
            throw new BusinessException("Queue name can not be blank");
        }

        if (distributionStrategy == null){
            distributionStrategy = DistributionStrategy.LEAST_BUSY;
        }

        var now = Instant.now();

        return new Queue(UUID.randomUUID(),
                         companyId,
                         name,
                         distributionStrategy,
                         now,
                         now,
                null);
    }

    public static Queue rehydrate(
                                    UUID id,
                                    UUID companyId,
                                    String name,
                                    DistributionStrategy distributionStrategy,
                                    Instant createdAt,
                                    Instant updatedAt,
                                    Instant deletedAt
    ){
        return new Queue(id, companyId, name, distributionStrategy, createdAt, updatedAt, deletedAt);
    }

    public static Queue hydrate(
            UUID id,
            UUID companyId,
            String name,
            DistributionStrategy distributionStrategy
    ){
        return new Queue(id, companyId, name, distributionStrategy, null, null, null);
    }

    public void update( String name,
                        DistributionStrategy distributionStrategy
    ) {
        if (DomainStrings.isNotBlank(name)) {
            this.name = name;
        }

        if (Objects.nonNull(distributionStrategy)) {
            this.distributionStrategy = distributionStrategy;
        }

        this.updatedAt = Instant.now();
    }

    public boolean isOpenForAttendance(LocalDateTime dateTime) {
        return settings.isOpenAt(QueueDateTime.now(dateTime));
    }
}
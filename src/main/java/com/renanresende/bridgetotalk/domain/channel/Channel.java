package com.renanresende.bridgetotalk.domain.channel;

import com.renanresende.bridgetotalk.domain.shared.exception.BusinessException;
import com.renanresende.bridgetotalk.domain.util.DomainStrings;

import java.time.Instant;
import java.util.UUID;

public class Channel {

    private UUID id;
    private UUID companyId;
    private ChannelType type;
    private String name;

    // Eg: phone number, bot id, widget id
    private String externalIdentifier;

    private boolean active;

    private QueueMapping queueMapping;

    private Instant createdAt;
    private Instant updatedAt;

    private Channel(UUID companyId,
                    ChannelType type,
                    String name,
                    String identifier,
                    UUID defaultQueueId) {
        this.companyId = companyId;
        this.type = type;
        this.name = name;
        this.externalIdentifier = identifier;
        this.queueMapping = QueueMapping.createWithDefaultQueue(defaultQueueId);

    }

    public static Channel create(UUID companyId,
                                 ChannelType type,
                                 String name,
                                 String identifier,
                                 UUID defaultQueueId) {

        if (DomainStrings.isBlank(name)) throw new BusinessException("Channel name is required");
        if (DomainStrings.isBlank(identifier)) throw new BusinessException("External identifier is required");
        if (defaultQueueId == null) throw new BusinessException("Default queue is required");

        return new Channel(companyId, type, name, identifier, defaultQueueId);
    }
}

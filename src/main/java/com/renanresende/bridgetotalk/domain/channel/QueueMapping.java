package com.renanresende.bridgetotalk.domain.channel;

import java.util.Map;
import java.util.UUID;

public class QueueMapping {

    private UUID defaultQueueId;

    //for more complex routing rules in the future
    private Map<String, UUID> routingRules;

    public UUID resolveQueue() {
        return defaultQueueId;
    }

    private QueueMapping(UUID defaultQueueId) {
        this.defaultQueueId = defaultQueueId;
    }

    public static QueueMapping createWithDefaultQueue(UUID defaultQueueId) {
        return new QueueMapping(defaultQueueId);
    }
}

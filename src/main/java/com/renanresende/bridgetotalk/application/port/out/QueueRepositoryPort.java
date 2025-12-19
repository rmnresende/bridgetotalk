package com.renanresende.bridgetotalk.application.port.out;

import com.renanresende.bridgetotalk.adapter.in.web.dto.queue.QueueFilter;
import com.renanresende.bridgetotalk.domain.Queue;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface QueueRepositoryPort {

    Queue save(Queue queue);

    Optional<Queue> findById(UUID id);

    Optional<Queue> findByIdAndCompanyId(UUID id, UUID companyId);

    Optional<Queue> findByCompanyIdAndName(UUID companyId, String name);

    List<Queue> findAllActiveQueuesByCompanyId(UUID companyId);

    List<Queue> filterQueuesByCompanyId(QueueFilter queueFilter, UUID companyId);

    void deleteQueue(UUID queueId, Instant deletedAt);
}
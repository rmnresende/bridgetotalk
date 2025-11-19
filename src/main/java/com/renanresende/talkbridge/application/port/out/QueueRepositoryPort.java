package com.renanresende.talkbridge.application.port.out;

import com.renanresende.talkbridge.domain.Queue;
import java.util.Optional;
import java.util.UUID;
import java.util.List;

public interface QueueRepositoryPort {

    Queue save(Queue queue);

    Optional<Queue> findById(UUID id);

    List<Queue> findAllByCompanyId(UUID companyId);
}
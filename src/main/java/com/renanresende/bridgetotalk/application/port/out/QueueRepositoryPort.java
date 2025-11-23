package com.renanresende.bridgetotalk.application.port.out;

import com.renanresende.bridgetotalk.domain.Queue;
import java.util.Optional;
import java.util.UUID;
import java.util.List;

public interface QueueRepositoryPort {

    Queue save(Queue queue);

    Optional<Queue> findById(UUID id);

    List<Queue> findAllByCompanyId(UUID companyId);
}
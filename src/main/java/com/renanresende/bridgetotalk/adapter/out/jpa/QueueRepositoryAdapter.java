package com.renanresende.bridgetotalk.adapter.out.jpa;

import com.renanresende.bridgetotalk.adapter.in.web.dto.queue.QueueFilter;
import com.renanresende.bridgetotalk.adapter.out.jpa.mapper.QueueJpaMapper;
import com.renanresende.bridgetotalk.adapter.out.jpa.spec.QueueEspecification;
import com.renanresende.bridgetotalk.adapter.out.jpa.spec.SortParams;
import com.renanresende.bridgetotalk.application.port.out.QueueRepositoryPort;
import com.renanresende.bridgetotalk.domain.attendance.Queue;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class QueueRepositoryAdapter implements QueueRepositoryPort {

    private final SpringDataQueueRepository queueRepository;
    private final QueueJpaMapper mapper;

    public QueueRepositoryAdapter(SpringDataQueueRepository queueRepo, QueueJpaMapper mapper) {
        this.queueRepository = queueRepo;
        this.mapper = mapper;
    }

    @Override
    public Queue save(Queue queue) {
        var entity = mapper.toEntity(queue);
        return mapper.toDomain(queueRepository.save(entity));
    }

    @Override
    public Optional<Queue> findById(UUID id) {
        return queueRepository.findById(id)
                              .map(mapper::toDomain);
    }

    @Override
    public Optional<Queue> findByIdAndCompanyId(UUID id, UUID companyId) {
         return queueRepository.findByIdAndCompanyId(id, companyId)
                               .map(mapper::toDomain);
    }

    @Override
    public Optional<Queue> findByCompanyIdAndName(UUID companyId, String name) {
        return queueRepository.findByCompanyIdAndName(companyId, name)
                              .map(mapper::toDomain);
    }

    @Override
    public List<Queue> findAllActiveQueuesByCompanyId(UUID companyId) {
        return  queueRepository.findAllActiveQueuesByCompanyId(companyId)
                               .stream()
                               .map(mapper::toDomain)
                               .toList();
    }

    @Override
    public List<Queue> filterQueuesByCompanyId(QueueFilter queueFilter, UUID companyId) {

        var especification = QueueEspecification.withOptionalFiltersByCompany(queueFilter, companyId);
        var sortField = SortParams.validateFieldToSort(queueFilter.queryOptions().sortBy().orElse(null), "queue");
        var sortDirection = SortParams.validateDirection(queueFilter.queryOptions().sortDirection().orElse(null));

        return queueRepository.findAll(especification, Sort.by(sortDirection, sortField))
                              .stream()
                              .map(mapper::toDomain)
                              .toList();
    }

    @Transactional
    @Override
    public void deleteQueue(UUID queueId, Instant deletedAt) {
        queueRepository.deleteQueue(queueId, deletedAt);
    }
}

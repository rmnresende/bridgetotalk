package com.renanresende.bridgetotalk.adapter.in.web;

import com.renanresende.bridgetotalk.adapter.in.web.dto.QueryOptions;
import com.renanresende.bridgetotalk.adapter.in.web.dto.agent.AgentDto;
import com.renanresende.bridgetotalk.adapter.in.web.dto.queue.*;
import com.renanresende.bridgetotalk.adapter.in.web.mapper.AgentDtoMapper;
import com.renanresende.bridgetotalk.adapter.in.web.mapper.QueueDtoMapper;
import com.renanresende.bridgetotalk.application.port.in.ManageQueueUseCase;
import com.renanresende.bridgetotalk.application.port.in.command.LinkQueueAgentCommand;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/queues")
public class QueueController {

    private final ManageQueueUseCase service;
    private final QueueDtoMapper mapper;
    private final AgentDtoMapper agentMapper;

    public QueueController(ManageQueueUseCase service,
                           AgentDtoMapper agentMapper,
                           QueueDtoMapper mapper) {
        this.service = service;
        this.mapper = mapper;
        this.agentMapper = agentMapper;
    }

    @PostMapping
    public ResponseEntity<ResponseQueueDto> createQueue(@RequestBody CreateQueueDto request){

        var command = mapper.toCreateCommand(request);
        var domain = service.createQueue(command);

        var uri = URI.create(String.format("/api/v1/queue/%s/company/%s", domain.getId(), domain.getCompanyId()));

        return ResponseEntity
                .created(uri)
                .body(mapper.toResponseDto(domain));
    }

    @GetMapping("/active/company/{companyId}")
    public ResponseEntity<List<ResponseQueueDto>> listQueuesByCompany(@PathVariable UUID companyId){

        var response = service.getAllActiveQueuesFromCompany(companyId)
                .stream()
                .map(mapper::toResponseDto)
                .toList();

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{queueId}")
    public ResponseEntity<Void> updateQueue(@PathVariable UUID queueId,
                                            @Valid @RequestBody UpdateQueueDto request){

        var command = mapper.toUpdateCommand(queueId, request);
        var response = service.updateQueue(queueId, command);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{queueId}/company/{companyId}")
    public ResponseEntity<Void> deleteQueue(@PathVariable UUID queueId,
                                            @PathVariable UUID companyId){

        service.deleteQueue(queueId, companyId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<ResponseQueueDto>> filterQueuesByCompany(@PathVariable UUID companyId,
                                                                @RequestParam(required = false) String name,
                                                                @RequestParam(required = false) boolean inactive,
                                                                @RequestParam(required = false) String sortBy,
                                                                @RequestParam(required = false) String sortDirection){
        var filter = new QueueFilter(
                Optional.ofNullable(name),
                new QueryOptions(
                        Optional.ofNullable(sortBy),
                        Optional.ofNullable(sortDirection),
                        inactive
                )
        );

        var response = service.filterQueuesByCompanyId(filter, companyId)
                .stream()
                .map(mapper::toResponseDto)
                .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{queueId}/agents")
    public ResponseEntity<List<AgentDto>> getAgentsFromQueue(@PathVariable UUID queueId){

        var agentsResponse = service.findAgentsByQueueId(queueId)
                .stream()
                .map(agentMapper::toDto)
                .toList();

        return ResponseEntity.ok(agentsResponse);
    }

    @PostMapping("{queueId}/agents/{agentId}")
    public ResponseEntity<Void> linkAgentToQueue(@PathVariable UUID queueId,
                                                 @PathVariable UUID agentId,
                                                 @Valid @RequestBody QueueAgentLinkDto request){

        var command = new LinkQueueAgentCommand(queueId, agentId, null, request.priority());
        service.linkAgentToQueue(command);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{queueId}/agents/{agentId}/companies/{companyId}")
    public ResponseEntity<Void> unlinkAgentFromQueue(@PathVariable UUID queueId,
                                                     @PathVariable UUID agentId){
        service.unlinkAgentFromQueue(agentId, queueId);
        return ResponseEntity.noContent().build();
    }
}

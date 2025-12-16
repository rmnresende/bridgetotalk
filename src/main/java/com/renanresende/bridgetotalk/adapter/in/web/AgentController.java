package com.renanresende.bridgetotalk.adapter.in.web;

import com.renanresende.bridgetotalk.adapter.in.web.dto.AgentDto;
import com.renanresende.bridgetotalk.adapter.in.web.dto.AgentFilter;
import com.renanresende.bridgetotalk.adapter.in.web.dto.UpdateAgentDto;
import com.renanresende.bridgetotalk.adapter.in.web.mapper.AgentDtoMapper;
import com.renanresende.bridgetotalk.adapter.in.web.mapper.CompanyDtoMapper;
import com.renanresende.bridgetotalk.application.service.ManagmentAgentService;
import com.renanresende.bridgetotalk.domain.AgentStatus;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/agents")
public class AgentController {

    private final ManagmentAgentService service;
    private final AgentDtoMapper mapper;
    private final CompanyDtoMapper companyDtoMapper;

    public AgentController(ManagmentAgentService agentService, AgentDtoMapper mapper, CompanyDtoMapper companyDtoMapper) {
        this.service = agentService;
        this.mapper = mapper;
        this.companyDtoMapper = companyDtoMapper;
    }

    @PostMapping
    public ResponseEntity<AgentDto> create(
            @RequestBody AgentDto request
    ) {

        var command = mapper.toCommand(request);
        var response = service.create(command);

        var uri = URI.create(String.format("/api/v1/agents/%s/company/%s", response.getId(), response.getCompanyId()));

        return ResponseEntity
                .created(uri)
                .body(mapper.toDto(response));
    }

    @GetMapping("/{id}/company/{companyId}")
    public ResponseEntity<AgentDto> getActiveAgentByEmailAndCompany(@PathVariable UUID id, @PathVariable UUID companyId){

        var domain = service.getActiveAgent(id, companyId);
        return ResponseEntity.ok(mapper.toDto(domain));
    }


    @GetMapping("/active/company/{companyId}")
    public ResponseEntity<List<AgentDto>> filterActiveAgentsByCompany(@PathVariable UUID companyId,
                                                                      @RequestParam(required = false) String name,
                                                                      @RequestParam(required = false) String email,
                                                                      @Parameter(
                                                                              description = "Agent status",
                                                                              schema = @Schema(implementation = AgentStatus.class)
                                                                      )
                                                                      @RequestParam(required = false) String status,
                                                                      @RequestParam(required = false) boolean inactive,
                                                                      @RequestParam(required = false) String sortBy,
                                                                      @RequestParam(required = false) String sortDirection){

        var agentStatus = AgentStatus.from(status);// se for um status invalido lança IllegalArgumentException
        var filter = new AgentFilter(
                Optional.ofNullable(name),
                Optional.ofNullable(email),
                Optional.ofNullable(agentStatus),
                Optional.ofNullable(sortBy),
                Optional.ofNullable(sortDirection),
                inactive
        );

        var response = service.filterActiveAgentsByCompanyId(filter, companyId)
                .stream()
                .map(mapper::toDto)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/company/{companyId}/email/{email}")
    public ResponseEntity<AgentDto> getActiveAgentByEmailAndCompany(@PathVariable UUID companyId, @PathVariable String email){

        var domain = service.findActiveAgentByCompanyIdAndEmail(companyId, email);
        return ResponseEntity.ok(mapper.toDto(domain));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> updateStatus(
            @PathVariable UUID id,
            @RequestBody UpdateAgentDto request)
    {
        service.updateAgentStatus(id, request.companyId(), request.status());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{agentId}/company/{companyId}")
    public ResponseEntity<Void> deleteAgent(@PathVariable UUID agentId, @PathVariable UUID companyId) {
        service.deleteAgent(agentId, companyId);
        return ResponseEntity.noContent().build();
    }
}

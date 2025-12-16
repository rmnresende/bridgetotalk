package com.renanresende.bridgetotalk.adapter.in.web;

import com.renanresende.bridgetotalk.adapter.in.web.dto.CreateCompanyDto;
import com.renanresende.bridgetotalk.adapter.in.web.dto.CompanySettingsUpdateDto;
import com.renanresende.bridgetotalk.adapter.in.web.dto.ResponseCompanyDto;
import com.renanresende.bridgetotalk.adapter.in.web.dto.UpdateCompanyStatusRequestDto;
import com.renanresende.bridgetotalk.adapter.in.web.mapper.CompanyDtoMapper;
import com.renanresende.bridgetotalk.adapter.in.web.mapper.CompanySettingsDtoMapper;
import com.renanresende.bridgetotalk.application.port.in.ManageCompanyUseCase;
import com.renanresende.bridgetotalk.domain.exception.BusinessException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/companies")
public class CompanyController {

    private final ManageCompanyUseCase service;
    private final CompanyDtoMapper mapper;
    private final CompanySettingsDtoMapper settingsMapper;

    public CompanyController(ManageCompanyUseCase service,
                             CompanyDtoMapper mapper,
                             CompanySettingsDtoMapper settingsMapper) {
        this.service = service;
        this.mapper = mapper;
        this.settingsMapper = settingsMapper;
    }

    @PostMapping
    public ResponseEntity<ResponseCompanyDto> create(
            @RequestBody CreateCompanyDto request
    ) {
        var domain = mapper.toDomain(request);
        var response = service.create(domain);

        return ResponseEntity
                .created(URI.create("/api/v1/companies/" + response.getId()))
                .body(mapper.toResponseDto(response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseCompanyDto> getById(@PathVariable UUID id) {
        var domain = service.get(id);
        return ResponseEntity.ok(mapper.toResponseDto(domain));
    }

    @GetMapping
    public ResponseEntity<List<ResponseCompanyDto>> list() {
        var companies = service.list()
                .stream()
                .map(mapper::toResponseDto)
                .toList();

        return ResponseEntity.ok(companies);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseCompanyDto> update(
            @PathVariable UUID id,
            @RequestBody CreateCompanyDto request
    ) throws BusinessException {

        var command = mapper.toCommand(id, request);
        var domain = service.update(command);
        return ResponseEntity.ok(mapper.toResponseDto(domain));
    }

    @PutMapping("/{id}/settings")
    public ResponseEntity<CompanySettingsUpdateDto> updateSettings(
            @PathVariable UUID id,
            @RequestBody CompanySettingsUpdateDto request
    ) throws BusinessException {

        var command = settingsMapper.toCommand(request);
        var domain = service.updateSettings(id, command);
        return ResponseEntity.ok(settingsMapper.toDto(domain));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> updateStatus(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateCompanyStatusRequestDto request) throws BusinessException {
        service.changeStatus(id, request.status());
        return ResponseEntity.noContent().build();
    }
}

package com.renanresende.bridgetotalk.domain.organization;

import com.renanresende.bridgetotalk.domain.shared.exception.ResourceNotFoundException;

import java.util.UUID;

public class CompanyNotFoundException extends ResourceNotFoundException {
    public CompanyNotFoundException(UUID id) {
        super("Company not found with id: " + id);
    }
}

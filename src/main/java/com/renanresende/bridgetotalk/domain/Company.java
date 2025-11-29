package com.renanresende.bridgetotalk.domain;

import com.renanresende.bridgetotalk.domain.exception.BusinessException;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Company {

    private UUID id;
    private String name;
    private String slug;
    private String email;
    private String phone;
    private String document;
    private CompanyStatus status;
    private CompanySettings settings;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;

    public void changeStatus(CompanyStatus newStatus) throws BusinessException {
        validateStatusTransition(this.status, newStatus);
        this.status = newStatus;
        this.updatedAt = Instant.now();
    }

    private void validateStatusTransition(CompanyStatus current, CompanyStatus target) throws BusinessException {
        if (current == target) {
            throw new BusinessException("A empresa já está com o status: " + target);
        }
    }
}

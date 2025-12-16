package com.renanresende.bridgetotalk.domain;

import com.renanresende.bridgetotalk.domain.exception.BusinessException;
import com.renanresende.bridgetotalk.domain.util.DomainStrings;
import lombok.*;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

import static com.renanresende.bridgetotalk.domain.Plan.BASIC;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
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

    public static Company createNew(
            String name,
            String slug,
            String email,
            String phone,
            String document,
            Plan plan
    ) {

        var now = Instant.now();
        return new Company(
                UUID.randomUUID(),
                name,
                slug,
                email,
                phone,
                document,
                CompanyStatus.ACTIVE,
                CompanySettings.createFromPlan(plan),//if null crate a basic plan
                now,
                now,
                null
        );
    }

    public static Company rehydrate(
            UUID id,
            String name,
            String slug,
            String email,
            String phone,
            String document,
            CompanyStatus status,
            CompanySettings settings,
            Instant createdAt,
            Instant updatedAt,
            Instant deletedAt
    ) {
        return new Company(
                id,
                name,
                slug,
                email,
                phone,
                document,
                status,
                settings,
                createdAt,
                updatedAt,
                deletedAt
        );
    }


    public void update( String name,
                        String slug,
                        String email,
                        String phone,
                        String document
    ) {
        if (DomainStrings.isNotBlank(name)) {
            this.name = name;
        }
        if (DomainStrings.isNotBlank(slug)) {
           this.slug = slug;
        }
        if (DomainStrings.isNotBlank(email)) {
            this.email = email;
        }
        if (DomainStrings.isNotBlank(phone)) {
            this.phone = phone;
        }
        if (DomainStrings.isNotBlank(document)) {
            this.document = document;
        }

        this.updatedAt = Instant.now();
    }

    public void changeStatus(CompanyStatus newStatus) {
        validateStatusTransition(this.status, newStatus);
        this.status = newStatus;
        this.updatedAt = Instant.now();
    }

    private void validateStatusTransition(CompanyStatus current, CompanyStatus target) {
        if (current == target) {
            throw new BusinessException("A empresa já está com o status: " + target);
        }
    }
}

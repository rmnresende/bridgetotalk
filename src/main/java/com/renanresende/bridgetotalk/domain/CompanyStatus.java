package com.renanresende.bridgetotalk.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.renanresende.bridgetotalk.config.CompanyStatusDeserializer;

@JsonDeserialize(using = CompanyStatusDeserializer.class)
public enum CompanyStatus {
    ACTIVE,     // empresa ativa e operando normalmente
    SUSPENDED,  // pagamentos atrasados / excedeu limites
    INACTIVE   // soft delete
}

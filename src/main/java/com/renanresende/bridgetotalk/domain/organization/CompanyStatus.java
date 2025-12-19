package com.renanresende.bridgetotalk.domain.organization;

public enum CompanyStatus {
    ACTIVE,     // empresa ativa e operando normalmente
    SUSPENDED,  // pagamentos atrasados / excedeu limites
    INACTIVE   // soft delete
}

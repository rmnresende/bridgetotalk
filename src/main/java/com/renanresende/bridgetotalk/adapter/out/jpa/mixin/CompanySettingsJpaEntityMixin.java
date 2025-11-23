package com.renanresende.bridgetotalk.adapter.out.jpa.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class CompanySettingsJpaEntityMixin {

    @JsonIgnore
    abstract Object getCompany(); // evita serialização recursiva
}

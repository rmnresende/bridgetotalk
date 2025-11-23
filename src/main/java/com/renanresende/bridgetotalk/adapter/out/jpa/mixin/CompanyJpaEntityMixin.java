package com.renanresende.bridgetotalk.adapter.out.jpa.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class CompanyJpaEntityMixin {

    @JsonIgnore
    abstract Object getSettings(); // ignorar relação 1-1 reversa (prevent loop)

}

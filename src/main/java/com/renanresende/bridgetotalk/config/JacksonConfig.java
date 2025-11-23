package com.renanresende.bridgetotalk.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.renanresende.bridgetotalk.adapter.out.jpa.entity.CompanyJpaEntity;
import com.renanresende.bridgetotalk.adapter.out.jpa.entity.CompanySettingsJpaEntity;
import com.renanresende.bridgetotalk.adapter.out.jpa.mixin.CompanyJpaEntityMixin;
import com.renanresende.bridgetotalk.adapter.out.jpa.mixin.CompanySettingsJpaEntityMixin;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer addMixIns() {
        return builder -> {
            builder.mixIn(CompanyJpaEntity.class, CompanyJpaEntityMixin.class);
            builder.mixIn(CompanySettingsJpaEntity.class, CompanySettingsJpaEntityMixin.class);
        };
    }
}

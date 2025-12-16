package com.renanresende.bridgetotalk.config;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.renanresende.bridgetotalk.adapter.out.jpa.entity.CompanyJpaEntity;
import com.renanresende.bridgetotalk.adapter.out.jpa.entity.CompanySettingsJpaEntity;
import com.renanresende.bridgetotalk.adapter.out.jpa.mixin.CompanyJpaEntityMixin;
import com.renanresende.bridgetotalk.adapter.out.jpa.mixin.CompanySettingsJpaEntityMixin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {

    @Bean
    public Module mixInModule() {
        SimpleModule module = new SimpleModule();

        module.setMixInAnnotation(CompanyJpaEntity.class, CompanyJpaEntityMixin.class);
        module.setMixInAnnotation(CompanySettingsJpaEntity.class, CompanySettingsJpaEntityMixin.class);

        return module;
    }
}

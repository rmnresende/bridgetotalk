package com.renanresende.bridgetotalk.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.renanresende.bridgetotalk.domain.CompanyStatus;

import java.io.IOException;

public class CompanyStatusDeserializer extends JsonDeserializer<CompanyStatus> {

    @Override
    public CompanyStatus deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException {

        String value = p.getText();

        // Tenta converter, mas não lança exceção se falhar
        try {
            return CompanyStatus.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            // Retorna null - deixa @ValidEnum validar
            return null;
        }
    }
}
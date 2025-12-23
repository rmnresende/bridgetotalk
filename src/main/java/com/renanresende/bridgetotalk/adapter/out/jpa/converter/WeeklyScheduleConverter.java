package com.renanresende.bridgetotalk.adapter.out.jpa.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.renanresende.bridgetotalk.adapter.out.jpa.mixin.DailyScheduleMixIn;
import com.renanresende.bridgetotalk.domain.attendance.DailySchedule;
import com.renanresende.bridgetotalk.domain.attendance.WeeklySchedule;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.time.DayOfWeek;
import java.util.Map;

@Converter(autoApply = true)
public class WeeklyScheduleConverter implements AttributeConverter<WeeklySchedule, String> {

    private static final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule())
                .registerModule(new ParameterNamesModule()) // Permite ler nomes de parâmetros de construtores
                .addMixIn(DailySchedule.class, DailyScheduleMixIn.class)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    @Override
    public String convertToDatabaseColumn(WeeklySchedule attribute) {
        if (attribute == null) return null;
        try {
            return mapper.writeValueAsString(attribute.getSchedules());
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Erro ao serializar", e);
        }
    }

    @Override
    public WeeklySchedule convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isBlank()) return null;
        try {
            var root = mapper.readTree(dbData);
            // Se o JSON vier embrulhado em "schedules", pegamos o nó interno
            var schedulesNode = root.has("schedules") ? root.get("schedules") : root;

            var map = mapper.convertValue(
                    schedulesNode,
                    new TypeReference<Map<DayOfWeek, DailySchedule>>() {}
            );
            return WeeklySchedule.of(map);
        } catch (Exception e) {
            throw new IllegalArgumentException("Falha ao converter WeeklySchedule: " + e.getMessage(), e);
        }
    }
}
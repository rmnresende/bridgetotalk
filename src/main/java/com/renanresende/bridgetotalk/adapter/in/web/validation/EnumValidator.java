package com.renanresende.bridgetotalk.adapter.in.web.validation;

import com.renanresende.bridgetotalk.adapter.in.web.validation.ValidEnum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.stream.Collectors;

public class EnumValidator implements ConstraintValidator<ValidEnum, Enum<?>> {

    private Class<? extends Enum<?>> enumClass;
    private String acceptedValuesMessage;  // ← Cache

    @Override
    public void initialize(ValidEnum annotation) {
        this.enumClass = annotation.enumClass();

        // Calcula uma vez só na inicialização
        this.acceptedValuesMessage = Arrays.stream(enumClass.getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.joining(", "));
    }

    @Override
    public boolean isValid(Enum<?> value, ConstraintValidatorContext context) {

        boolean isValid = value != null && Arrays.asList(enumClass.getEnumConstants()).contains(value);

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "Invalid status. Accepted values: " + acceptedValuesMessage
            ).addConstraintViolation();
        }

        return isValid;
    }
}
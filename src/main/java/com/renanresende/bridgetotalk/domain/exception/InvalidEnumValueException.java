package com.renanresende.bridgetotalk.domain.exception;

public class InvalidEnumValueException extends RuntimeException {

    private final String parameter;
    private final String value;
    private final String allowedValues;

    public InvalidEnumValueException(String parameter, String value, String allowedValues) {
        super("Invalid value for parameter '" + parameter + "'");
        this.parameter = parameter;
        this.value = value;
        this.allowedValues = allowedValues;
    }

    public String getParameter() {
        return parameter;
    }

    public String getValue() {
        return value;
    }

    public String getAllowedValues() {
        return allowedValues;
    }
}

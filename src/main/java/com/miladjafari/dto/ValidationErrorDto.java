package com.miladjafari.dto;

import javax.validation.ConstraintViolation;

public class ValidationErrorDto {
    private ReasonCode code;
    private String param;
    private String description;

    public ReasonCode getCode() {
        return code;
    }

    public String getParam() {
        return param;
    }

    public String getDescription() {
        return description;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private ValidationErrorDto instance = new ValidationErrorDto();

        public Builder code(ReasonCode code) {
            instance.code = code;
            return this;
        }

        public Builder description(String description) {
            instance.description = description;
            return this;
        }

        public <T> Builder constraintViolation(ConstraintViolation<T> constraintViolation) {
            instance.code = ReasonCode.INVALID_VALUE;
            instance.param = constraintViolation.getPropertyPath().toString();
            instance.description = constraintViolation.getMessage();

            return this;
        }

        public ValidationErrorDto build() {
            return instance;
        }
    }
}

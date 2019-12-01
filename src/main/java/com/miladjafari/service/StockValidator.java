package com.miladjafari.service;

import com.miladjafari.dto.*;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class StockValidator {

    @Inject
    StockDao stockService;

    @Inject
    Validator validator;

    public List<ValidationErrorDto> validate(StockCreateRequestDto stockCreateRequest) {
        List<ValidationErrorDto> errors = validateFormat(stockCreateRequest);
        validateStockNameIfErrorsIsEmpty(stockCreateRequest.getName(), errors);

        return errors;
    }

    public List<ValidationErrorDto> validate(StockUpdateRequestDto stockUpdateRequest) {
        List<ValidationErrorDto> errors = validateFormat(stockUpdateRequest);
        validateStockIdIfErrorIsEmpty(stockUpdateRequest.getId(), errors);

        return errors;
    }

    public <T> List<ValidationErrorDto> validateFormat(T stockUpdateRequestDto) {
        Set<ConstraintViolation<T>> validations = validator.validate(stockUpdateRequestDto);
        return validations.stream()
                .map(cv -> ValidationErrorDto.builder().constraintViolation(cv).build())
                .collect(Collectors.toList());
    }

    private void validateStockNameIfErrorsIsEmpty(String stockName, List<ValidationErrorDto> errors) {
        if (errors.isEmpty()) {
            Optional<StockDto> stockDto = stockService.findByName(stockName);
            if (stockDto.isPresent()) {
                errors.add(ValidationErrorDto.builder()
                        .code(ReasonCode.STOCK_IS_EXIST)
                        .description("Stock name is already exist")
                        .build());
            }
        }
    }

    private void validateStockIdIfErrorIsEmpty(String id, List<ValidationErrorDto> errors) {
        if (errors.isEmpty()) {
            Optional<StockDto> stockDto = stockService.findById(Long.valueOf(id));
            if (!stockDto.isPresent()) {
                errors.add(ValidationErrorDto.builder()
                        .code(ReasonCode.STOCK_NOT_FOUND)
                        .description("Stock not found")
                        .build());
            }
        }
    }
}

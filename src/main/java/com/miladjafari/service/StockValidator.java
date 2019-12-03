package com.miladjafari.service;

import com.miladjafari.dao.Stock;
import com.miladjafari.dao.StockDao;
import com.miladjafari.dto.*;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class StockValidator {

    @Inject
    StockDao stockDao;

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

    public List<ValidationErrorDto> validate(String stockId) {
        List<ValidationErrorDto> errors = new ArrayList<>();
        if (!Optional.ofNullable(stockId).isPresent()) {
            errors.add(ValidationErrorDto.builder()
                    .code(ReasonCode.INVALID_VALUE)
                    .description("Stock id must be only digit")
                    .param("stockId")
                    .build()
            );
        }

        validateStockIdIfErrorIsEmpty(stockId, errors);

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
            Optional<Stock> stock = stockDao.findByName(stockName);
            if (stock.isPresent()) {
                errors.add(ValidationErrorDto.builder()
                        .code(ReasonCode.STOCK_IS_EXIST)
                        .description("Stock name is already exist")
                        .build());
            }
        }
    }

    private void validateStockIdIfErrorIsEmpty(String id, List<ValidationErrorDto> errors) {
        if (errors.isEmpty()) {
            Optional<Stock> stock = Optional.ofNullable(stockDao.findById(Long.valueOf(id)));
            if (!stock.isPresent()) {
                errors.add(ValidationErrorDto.builder()
                        .code(ReasonCode.STOCK_NOT_FOUND)
                        .description("Stock not found")
                        .build());
            }
        }
    }
}

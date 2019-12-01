package com.miladjafari.service;

import com.miladjafari.dto.*;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.miladjafari.dto.ReasonCode.STOCK_NOT_FOUND;

@ApplicationScoped
public class StockController {

    @Inject
    StockValidator validator;

    @Inject
    StockDao stockDao;

    public List<StockDto> findAll() {
        return stockDao.findAll();
    }

    public ServiceResponseDto findById(Long id) {
        Optional<StockDto> stock = stockDao.findById(id);
        return stock.map(stockDto -> ServiceResponseDto.builder().ok(stockDto).build())
                .orElseGet(() -> {
                    ValidationErrorDto error = ValidationErrorDto.builder()
                            .code(STOCK_NOT_FOUND)
                            .description("Stock not found")
                            .build();

                    return ServiceResponseDto.builder()
                            .error(error)
                            .notFound()
                            .build();
                });
    }

    public ServiceResponseDto create(StockCreateRequestDto stockCreateRequest) {
        ServiceResponseDto.Builder responseBuilder = ServiceResponseDto.builder();

        List<ValidationErrorDto> validationErrors = validator.validate(stockCreateRequest);
        if (validationErrors.isEmpty()) {
            stockDao.add(StockDto.builder().stockCreateRequest(stockCreateRequest).build());
            responseBuilder.ok();

        } else {
            responseBuilder.badRequest().errors(validationErrors);
        }

        return responseBuilder.build();
    }

    public ServiceResponseDto update(StockUpdateRequestDto stockUpdateRequest) {
        ServiceResponseDto.Builder responseBuilder = ServiceResponseDto.builder();

        List<ValidationErrorDto> validationErrors = validator.validate(stockUpdateRequest);
        if (validationErrors.isEmpty()) {
            Optional<StockDto> stock = stockDao.findById(Long.valueOf(stockUpdateRequest.getId()));
            stock.ifPresent(stockDto -> {
                stockDto.setPrice(new BigDecimal(stockUpdateRequest.getPrice()));
                stockDao.update(stockDto);
            });

            responseBuilder.ok();
        } else {
            responseBuilder.badRequest().errors(validationErrors);
        }

        return responseBuilder.build();
    }
}

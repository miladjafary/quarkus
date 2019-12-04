package com.miladjafari.service;

import com.miladjafari.dao.Stock;
import com.miladjafari.dao.StockDao;
import com.miladjafari.dto.*;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.miladjafari.dto.ReasonCode.STOCK_NOT_FOUND;

@ApplicationScoped
public class StockService {

    @Inject
    StockValidator validator;

    @Inject
    StockDao stockDao;

    public List<StockDto> findAll() {
        List<StockDto> stocks = stockDao.findAll().stream()
                .map(stock -> StockDto.builder().stock(stock).build())
                .collect(Collectors.toList());
        return stocks;
    }

    public ServiceResponseDto findById(Long id) {
        Optional<Stock> stock = Optional.ofNullable(stockDao.findById(id));

        return stock.map(stockEntity -> {
            StockDto stockDto = StockDto.builder().stock(stockEntity).build();
            return ServiceResponseDto.builder().ok(stockDto).build();

        }).orElseGet(() -> {
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

    @Transactional
    public ServiceResponseDto create(StockCreateRequestDto stockCreateRequest) {
        ServiceResponseDto.Builder responseBuilder = ServiceResponseDto.builder();

        List<ValidationErrorDto> validationErrors = validator.validate(stockCreateRequest);
        if (validationErrors.isEmpty()) {
            stockDao.save(Stock.builder().stockCreateRequest(stockCreateRequest).build());
            responseBuilder.ok();

        } else {
            responseBuilder.badRequest().errors(validationErrors);
        }

        return responseBuilder.build();
    }

    @Transactional
    public ServiceResponseDto update(StockUpdateRequestDto stockUpdateRequest) {
        ServiceResponseDto.Builder responseBuilder = ServiceResponseDto.builder();

        List<ValidationErrorDto> validationErrors = validator.validate(stockUpdateRequest);
        if (validationErrors.isEmpty()) {
            Long stockId = Long.valueOf(stockUpdateRequest.getId());
            Optional<Stock> stock = Optional.ofNullable(stockDao.findById(stockId));
            stock.ifPresent(targetStock -> {
                targetStock.setPrice(new BigDecimal(stockUpdateRequest.getPrice()));
                targetStock.setLastUpdate(LocalDateTime.now());
                stockDao.save(targetStock);
            });

            responseBuilder.ok();
        } else {
            responseBuilder.badRequest().errors(validationErrors);
        }

        return responseBuilder.build();
    }

    @Transactional
    public ServiceResponseDto delete(String id) {
        ServiceResponseDto.Builder responseBuilder = ServiceResponseDto.builder();

        List<ValidationErrorDto> validationErrors = validator.validate(id);
        if (validationErrors.isEmpty()) {
            stockDao.delete(Long.valueOf(id));
            responseBuilder.ok();
        } else {
            responseBuilder.badRequest().errors(validationErrors);
        }

        return responseBuilder.build();
    }
}

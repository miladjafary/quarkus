package com.miladjafari.service;

import com.miladjafari.dto.StockCreateRequestDto;
import com.miladjafari.dto.StockDto;
import com.miladjafari.dto.StockUpdateRequestDto;
import com.miladjafari.dto.ValidationErrorDto;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class StockController {

    @Inject
    StockValidator validator;

    @Inject
    StockService stockService;

    public List<StockDto> findAll() {
        return stockService.findAll();
    }

    public Optional<StockDto> findById(Long id) {
        return stockService.findById(id);
    }

    public List<ValidationErrorDto> create(StockCreateRequestDto stockCreateRequest) {
        List<ValidationErrorDto> validationErrors = validator.validate(stockCreateRequest);

        if (validationErrors.isEmpty()) {
            stockService.add(StockDto.builder().stockCreateRequest(stockCreateRequest).build());
        }

        return validationErrors;
    }

    public List<ValidationErrorDto> update(StockUpdateRequestDto stockUpdateRequest) {
        List<ValidationErrorDto> validationErrors = validator.validate(stockUpdateRequest);

        if (validationErrors.isEmpty()) {
            StockDto stock = findById(Long.valueOf(stockUpdateRequest.getId())).get();
            stock.setPrice(new BigDecimal(stockUpdateRequest.getPrice()));

            stockService.update(stock);
        }

        return validationErrors;
    }


}

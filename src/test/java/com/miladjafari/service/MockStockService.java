package com.miladjafari.service;

import com.miladjafari.dto.*;
import io.quarkus.test.Mock;

import javax.enterprise.context.ApplicationScoped;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.miladjafari.dto.ReasonCode.STOCK_NOT_FOUND;

@Mock
@ApplicationScoped
public class MockStockService extends StockService {

    @Override
    public List<StockDto> findAll() {
        List<StockDto> stocks = new ArrayList<StockDto>() {{
            add(StockDto.builder().id(1).name("Milad").price(new BigDecimal("1000")).lastUpdate("2019-12-01T10:54:00").build());
            add(StockDto.builder().id(3).name("Elena").price(new BigDecimal("2000")).lastUpdate("2019-12-01T10:55:00").build());
            add(StockDto.builder().id(4).name("Ninia").price(new BigDecimal("3000")).lastUpdate("2019-12-01T10:56:00").build());
        }};

        return stocks;
    }

    @Override
    public ServiceResponseDto findById(Long id) {
        final Long VALID_STOCK_ID = 1L;

        if (id.equals(VALID_STOCK_ID)) {
            StockDto mockStock = StockDto.builder().id(1).name("Milad").price(new BigDecimal("1000")).lastUpdate("2019-12-01T10:54:00").build();
            return ServiceResponseDto.builder().ok(mockStock).build();
        }

        ValidationErrorDto error = ValidationErrorDto.builder()
                .code(STOCK_NOT_FOUND)
                .description("Stock not found")
                .build();

        return ServiceResponseDto.builder()
                .error(error)
                .notFound()
                .build();
    }
}

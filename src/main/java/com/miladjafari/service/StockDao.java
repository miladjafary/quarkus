package com.miladjafari.service;

import com.miladjafari.dto.StockDto;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Validator;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class StockDao {
    private List<StockDto> stocks = new ArrayList<>();

    @Inject
    Validator validator;

    @PostConstruct
    public void initStocks() {
        stocks.add(StockDto.builder().id(1).name("Milad").price(new BigDecimal("1000")).lastUpdate("2019-12-01T10:54:00").build());
        stocks.add(StockDto.builder().id(3).name("Elena").price(new BigDecimal("2000")).lastUpdate("2019-12-01T10:55:00").build());
        stocks.add(StockDto.builder().id(4).name("Ninia").price(new BigDecimal("3000")).lastUpdate("2019-12-01T10:56:00").build());
    }

    public List<StockDto> findAll() {
        return stocks;
    }

    public Optional<StockDto> findById(Long id) {
        return stocks.stream().filter(stockDto -> stockDto.getId().equals(id)).findFirst();

    }

    public Optional<StockDto> findByName(String name) {
        return stocks.stream().filter(stockDto -> stockDto.getName().equals(name)).findFirst();
    }

    public void add(StockDto stockDto) {
        stocks.add(stockDto);
    }

    public void update(StockDto stock) {

    }

    public void delete (Long id) {
        Optional<StockDto> stock =findById(id);
        stock.ifPresent(stockDto -> stocks.remove(stockDto));
    }
}

package com.miladjafari.dao;

import io.quarkus.test.Mock;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;

@Mock
@ApplicationScoped
public class MockStockDao extends StockDao {

    @Override
    public Optional<Stock> findByName(String name) {
        final String EXIST_STOCK_NAME = "Milad";
        if (name.equals(EXIST_STOCK_NAME)) {
            return Optional.ofNullable(Stock.builder().name(EXIST_STOCK_NAME).price("1000").lastUpdate("2019-12-01T10:54:00").build());
        }

        return super.findByName(name);
    }
}
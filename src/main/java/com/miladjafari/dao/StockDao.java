package com.miladjafari.dao;

import com.miladjafari.dto.StockDto;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class StockDao implements PanacheRepository<Stock> {
    private List<StockDto> stocks = new ArrayList<>();

    @Inject
    Validator validator;

    @PostConstruct
    public void initStocks() {
        save(Stock.builder().name("Milad").price("1000").lastUpdate("2019-12-01T10:54:00").build());
        save(Stock.builder().name("Elena").price("2000").lastUpdate("2019-12-01T10:55:00").build());
        save(Stock.builder().name("Ninia").price("3000").lastUpdate("2019-12-01T10:56:00").build());
    }

    public Optional<StockDto> findByName(String name) {
        return stocks.stream().filter(stockDto -> stockDto.getName().equals(name)).findFirst();
    }

    @Transactional
    public void save(Stock stock) {
        stock.persist();
    }

    @Transactional
    public void delete(Long id) {
        Optional.ofNullable(findById(id))
                .ifPresent(PanacheEntityBase::delete);
    }
}

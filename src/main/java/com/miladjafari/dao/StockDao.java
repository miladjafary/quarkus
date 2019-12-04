package com.miladjafari.dao;

import com.miladjafari.dto.StockDto;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class StockDao implements PanacheRepositoryBase<Stock,Long> {
    private List<StockDto> stocks = new ArrayList<>();

    @Inject
    Validator validator;

    public Optional<Stock> findByName(String name) {
        return Optional.ofNullable(Stock.find("name", name).firstResult());
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

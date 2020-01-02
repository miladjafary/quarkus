package com.miladjafari.dao;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.Optional;

@ApplicationScoped
public class StockDao implements PanacheRepositoryBase<Stock,Long> {

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

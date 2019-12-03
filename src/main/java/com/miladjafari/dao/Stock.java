package com.miladjafari.dao;

import com.miladjafari.dto.StockCreateRequestDto;
import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Stock extends PanacheEntity {

    public String name;
    public BigDecimal price;
    public LocalDateTime lastUpdate;

    public String getName() {
        return name;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder {
        private Stock instance = new Stock();

        public Builder name(String name) {
            instance.name = name;
            return this;
        }

        public Builder price(String price) {
            instance.price = new BigDecimal(price);
            return this;
        }

        public Builder lastUpdate(String dateString) {
            instance.lastUpdate = LocalDateTime.parse(dateString);
            return this;
        }

        public Builder stockCreateRequest(StockCreateRequestDto stockCreateRequestDto) {
            instance.lastUpdate = LocalDateTime.now();

            instance.name = stockCreateRequestDto.getName();
            instance.price = new BigDecimal(stockCreateRequestDto.getPrice());

            return this;
        }

        public Stock build(){
            return instance;
        }
    }
}

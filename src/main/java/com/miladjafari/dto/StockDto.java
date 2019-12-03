package com.miladjafari.dto;

import com.miladjafari.dao.Stock;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class StockDto {
    private Long id;
    private String name;
    private BigDecimal price;
    private LocalDateTime lastUpdate;

    public StockDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockDto stockDto = (StockDto) o;
        return Objects.equals(id, stockDto.id) &&
                Objects.equals(name, stockDto.name) &&
                Objects.equals(price, stockDto.price) &&
                Objects.equals(lastUpdate, stockDto.lastUpdate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, lastUpdate);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private StockDto instance = new StockDto();

        public Builder id(Integer id) {
            instance.id = Long.valueOf(id);
            return this;
        }

        public Builder name(String name) {
            instance.name = name;
            return this;
        }

        public Builder price(BigDecimal price) {
            instance.price = price;
            return this;
        }

        public Builder lastUpdate(String dateString) {
            instance.lastUpdate = LocalDateTime.parse(dateString);
            return this;
        }

        public Builder stock(Stock entity){
            instance.id = entity.id;
            instance.name = entity.name;
            instance.price = entity.price;
            instance.lastUpdate = entity.lastUpdate;

            return this;
        }

        public StockDto build() {
            return instance;
        }
    }
}

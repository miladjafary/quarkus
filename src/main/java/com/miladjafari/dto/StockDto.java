package com.miladjafari.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Random;

public class StockDto {
    private Long id;
    private String name;
    private BigDecimal price;
    private Date lastUpdate;

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

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
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

        public Builder lastUpdate(Date lastUpdate) {
            instance.lastUpdate = lastUpdate;
            return this;
        }

        public Builder stockCreateRequest(StockCreateRequestDto stockCreateRequestDto) {
            instance.id = new Random().nextLong();
            instance.lastUpdate = new Date();

            instance.name = stockCreateRequestDto.getName();
            instance.price = new BigDecimal(stockCreateRequestDto.getPrice());

            return this;
        }

        public StockDto build() {
            return instance;
        }
    }
}

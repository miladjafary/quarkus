package com.miladjafari.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Objects;

public class StockUpdateRequestDto {

    @NotBlank(message = "Id is required")
    @Pattern(regexp="[0-9]+" , message = "Id must be only digits")
    private String id;

    @NotBlank(message = "Price is required")
    @Pattern(regexp="[0-9]+" , message = "Price must be only digits")
    private String price;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockUpdateRequestDto that = (StockUpdateRequestDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, price);
    }
}

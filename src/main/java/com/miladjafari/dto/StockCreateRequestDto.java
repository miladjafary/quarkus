package com.miladjafari.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Objects;

public class StockCreateRequestDto {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Price is required")
    @Pattern(regexp="^[0-9]+(\\.[0-9]{1,2})?$" , message = "Price must be only digits")
    @Size(max = 15, message = "Price can have only 15 digits")
    private String price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        StockCreateRequestDto that = (StockCreateRequestDto) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price);
    }
}

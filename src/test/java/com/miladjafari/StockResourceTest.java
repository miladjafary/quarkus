package com.miladjafari;

import com.miladjafari.dto.ReasonCode;
import com.miladjafari.dto.StockCreateRequestDto;
import com.miladjafari.dto.StockDto;
import com.miladjafari.dto.ValidationErrorDto;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import javax.json.bind.JsonbBuilder;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class StockResourceTest {
    private static final String NAME_IS_REQUIRED = "Name is required";
    private static final String PRICE_IS_REQUIRED = "Price is required";
    private static final String PRICE_MUST_BE_ONLY_DIGITS = "Price must be only digits";

    private static final String URL_STOCK_LIST = "/api/stocks";
    private static final String URL_STOCK_CREATE = "/api/stocks";
    private static final String URL_STOCK_READ_ONE = "/api/stocks/{id}";

    @Test
    public void testSuccessGetListOfStocks() {
        List<StockDto> expectedStocks = new ArrayList<>();
        expectedStocks.add(StockDto.builder().id(1).name("Milad").price(new BigDecimal("1000")).lastUpdate("2019-12-01T10:54:00").build());
        expectedStocks.add(StockDto.builder().id(3).name("Elena").price(new BigDecimal("2000")).lastUpdate("2019-12-01T10:55:00").build());
        expectedStocks.add(StockDto.builder().id(4).name("Ninia").price(new BigDecimal("3000")).lastUpdate("2019-12-01T10:56:00").build());

        String expectedStocksJson = JsonbBuilder.create().toJson(expectedStocks);
        given()
                .when().get(URL_STOCK_LIST)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body(is(expectedStocksJson));
    }

    @Test
    public void testSuccessGetOneStock() {
        final Integer STOCK_ID = 1;
        StockDto expectedStock = StockDto.builder().id(1).name("Milad").price(new BigDecimal("1000")).lastUpdate("2019-12-01T10:54:00").build();

        String expectedStockJson = JsonbBuilder.create().toJson(expectedStock);
        given()
                .when().get(URL_STOCK_READ_ONE, STOCK_ID)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body(is(expectedStockJson));
    }

    @Test
    public void testFailGetOneStockIfStockIdNotFound() {
        final Integer NOT_EXIST_STOCK_ID = 10;
        List<ValidationErrorDto> expectedErrors = new ArrayList<>();
        expectedErrors.add(ValidationErrorDto.builder().code(ReasonCode.STOCK_NOT_FOUND).description("Stock not found").build());

        String expectedErrorsJson = JsonbBuilder.create().toJson(expectedErrors);
        given()
                .when().get(URL_STOCK_READ_ONE, NOT_EXIST_STOCK_ID)
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body(is(expectedErrorsJson));
    }

    @Test
    @Ignore
    public void testFailCreateStockIfRequiredFieldsWereNotSent() {
        StockCreateRequestDto stockCreateRequestDto = new StockCreateRequestDto();
        stockCreateRequestDto.setName(null);
        stockCreateRequestDto.setPrice(null);

        String stockCreateRequestJson = JsonbBuilder.create().toJson(stockCreateRequestDto);

        List<ValidationErrorDto> expectedErrors = new ArrayList<>();
        expectedErrors.add(ValidationErrorDto.builder().code(ReasonCode.INVALID_VALUE).description(PRICE_IS_REQUIRED).param("price").build());
        expectedErrors.add(ValidationErrorDto.builder().code(ReasonCode.INVALID_VALUE).description(NAME_IS_REQUIRED).param("name").build());
        expectedErrors.add(ValidationErrorDto.builder().code(ReasonCode.INVALID_VALUE).description(PRICE_MUST_BE_ONLY_DIGITS).param("price").build());

        String expectedErrorsJson = JsonbBuilder.create().toJson(expectedErrors);
        given()
                .body(stockCreateRequestJson)
                .contentType(MediaType.APPLICATION_JSON)
//                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .post(URL_STOCK_CREATE)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(is(expectedErrorsJson));
    }

}
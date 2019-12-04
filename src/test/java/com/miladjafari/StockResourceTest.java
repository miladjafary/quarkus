package com.miladjafari;

import com.miladjafari.dto.*;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import javax.json.bind.JsonbBuilder;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
class StockResourceTest {

    private static final String URL_STOCK_LIST = "/api/stocks";
    private static final String URL_STOCK_CREATE = "/api/stocks";
    private static final String URL_STOCK_UPDATE = "/api/stocks/{id}";
    private static final String URL_STOCK_DELETE = "/api/stocks/{id}";
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
    public void testFailCreateStockIfRequiredFieldsAreNotSent() {
        Integer expectedErrorsCounts = 2;

        StockCreateRequestDto stockCreateRequestDto = new StockCreateRequestDto();
        stockCreateRequestDto.setName(null);
        stockCreateRequestDto.setPrice(null);

        String stockCreateRequestJson = JsonbBuilder.create().toJson(stockCreateRequestDto);

        Response response = given()
                .body(stockCreateRequestJson)
                .contentType(MediaType.APPLICATION_JSON)
                .post(URL_STOCK_CREATE)
                .then().assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract()
                .response();

        assertThat(response.jsonPath().getList("$").size(), is(expectedErrorsCounts));
    }

    @Test
    public void testSuccessCreateStockIfAllFieldAreValid() {
        StockCreateRequestDto stockCreateRequestDto = new StockCreateRequestDto();
        stockCreateRequestDto.setName("Not Exist Name");
        stockCreateRequestDto.setPrice("1100");

        String stockCreateRequestJson = JsonbBuilder.create().toJson(stockCreateRequestDto);

        given()
                .body(stockCreateRequestJson)
                .contentType(MediaType.APPLICATION_JSON)
                .post(URL_STOCK_CREATE)
                .then().assertThat()
                .statusCode(HttpStatus.SC_OK)
        ;
    }

    @Test
    public void testSuccessUpdateStockIfAllFieldAreValid() {
        final Long STOCK_ID = 1L;
        StockUpdateRequestDto stockCreateRequestDto = new StockUpdateRequestDto();
        stockCreateRequestDto.setPrice("1100");

        String stockCreateRequestJson = JsonbBuilder.create().toJson(stockCreateRequestDto);

        given()
                .body(stockCreateRequestJson)
                .contentType(MediaType.APPLICATION_JSON)
                .put(URL_STOCK_UPDATE, STOCK_ID)
                .then().assertThat()
                .statusCode(HttpStatus.SC_OK)
        ;
    }

    @Test
    public void testSuccessDeleteStock() {
        final Long STOCK_ID = 3L;

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .delete(URL_STOCK_DELETE, STOCK_ID)
                .then().assertThat()
                .statusCode(HttpStatus.SC_OK)
        ;
    }

}
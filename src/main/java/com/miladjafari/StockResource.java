package com.miladjafari;

import com.miladjafari.dto.ServiceResponseDto;
import com.miladjafari.dto.StockCreateRequestDto;
import com.miladjafari.dto.StockDto;
import com.miladjafari.dto.StockUpdateRequestDto;
import com.miladjafari.service.StockService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/api/stocks")
public class StockResource {

    @Inject
    StockService stockService;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<StockDto> findAll() {
        return stockService.findAll();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("id") Long id) {
        ServiceResponseDto serviceResponseDto = stockService.findById(id);
        return serviceResponseDto.getJaxRsResponse();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createStock(StockCreateRequestDto createRequest) {
        ServiceResponseDto createStockResponse = stockService.create(createRequest);
        return createStockResponse.getJaxRsResponse();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateStock(@PathParam("id") String id, StockUpdateRequestDto updateRequest) {
        updateRequest.setId(id);
        ServiceResponseDto updateResponse = stockService.update(updateRequest);

        return updateResponse.getJaxRsResponse();
    }

    @DELETE
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteStock(@PathParam("id") String id) {
        ServiceResponseDto updateResponse = stockService.delete(id);
        return updateResponse.getJaxRsResponse();
    }

}
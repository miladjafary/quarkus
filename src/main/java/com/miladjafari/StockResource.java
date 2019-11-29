package com.miladjafari;

import com.miladjafari.dto.StockCreateRequestDto;
import com.miladjafari.dto.StockDto;
import com.miladjafari.dto.StockUpdateRequestDto;
import com.miladjafari.dto.ValidationErrorDto;
import com.miladjafari.exception.StockNotFoundException;
import com.miladjafari.service.StockController;
import com.miladjafari.service.StockService;

import javax.inject.Inject;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("/api/stocks")
public class StockResource {

    @Inject
    StockController stockController;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<StockDto> findAll() {
        return stockController.findAll();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("id") Long id) {
        Optional<StockDto> stock = stockController.findById(id);

        return stock.map(stockDto -> Response.ok(stockDto).build())
                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND).build());
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createStock(StockCreateRequestDto createRequest) {
        List<ValidationErrorDto> errors = stockController.create(createRequest);

        if (errors.isEmpty()) {
            return Response.ok().build();
        }

        return Response.status(Response.Status.BAD_REQUEST)
                .entity(errors)
                .build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateStock(@PathParam("id") String id, StockUpdateRequestDto updateRequest) {
        updateRequest.setId(id);
        List<ValidationErrorDto> errors = stockController.update(updateRequest);

        if (errors.isEmpty()) {
            return Response.ok().build();
        }

        return Response.status(Response.Status.BAD_REQUEST)
                .entity(errors)
                .build();
    }
}
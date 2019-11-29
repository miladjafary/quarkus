package com.miladjafari.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class StockNotFoundExceptionMapper implements ExceptionMapper<StockNotFoundException> {
    @Override
    public Response toResponse(StockNotFoundException e) {
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}

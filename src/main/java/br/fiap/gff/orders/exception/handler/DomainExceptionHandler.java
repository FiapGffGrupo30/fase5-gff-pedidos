package br.fiap.gff.orders.exception.handler;

import br.fiap.gff.orders.dto.RestErrorResponse;
import br.fiap.gff.orders.exception.base.DomainException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class DomainExceptionHandler implements ExceptionMapper<DomainException> {

    @Override
    public Response toResponse(DomainException e) {
        RestErrorResponse errorResponse = new RestErrorResponse();
        errorResponse.setMessage(e.getMessage());
        errorResponse.setStatusCode(Response.Status.BAD_REQUEST.getStatusCode());
        return Response.status(Response.Status.BAD_REQUEST).entity(errorResponse).build();
    }

}

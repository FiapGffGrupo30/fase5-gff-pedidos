package br.fiap.gff.orders.exception;

import br.fiap.gff.orders.dto.RestErroResponse;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class DomainExceptionHandler implements ExceptionMapper<DomainException> {

    @Override
    public Response toResponse(DomainException e) {
        RestErroResponse errorResponse = new RestErroResponse();
        errorResponse.setMessage(e.getMessage());
        errorResponse.setStatusCode(Response.Status.BAD_REQUEST.getStatusCode());
        return Response.status(Response.Status.BAD_REQUEST).entity(errorResponse).build();
    }

}

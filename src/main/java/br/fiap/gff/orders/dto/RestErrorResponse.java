package br.fiap.gff.orders.dto;

import jakarta.ws.rs.core.Response;
import lombok.Data;

@Data
public class RestErrorResponse {

    private String message;
    private Integer statusCode;
    private Response.Status status;

}

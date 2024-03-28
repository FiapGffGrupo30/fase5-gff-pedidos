package br.fiap.gff.orders.dto;

import lombok.Data;

@Data
public class RestErrorResponse {

    private String message;
    private Integer statusCode;

}

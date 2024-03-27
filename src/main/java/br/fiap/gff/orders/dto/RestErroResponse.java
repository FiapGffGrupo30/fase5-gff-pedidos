package br.fiap.gff.orders.dto;

import lombok.Data;

@Data
public class RestErroResponse {

    private String message;
    private Integer statusCode;

}

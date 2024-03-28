package br.fiap.gff.orders.dto;

import lombok.Data;

@Data
public class OrderItemRequest {

    private String productId;
    private Integer quantity;

}

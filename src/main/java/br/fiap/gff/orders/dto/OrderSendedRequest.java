package br.fiap.gff.orders.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class OrderSendedRequest {

    private Long customerId;
    private UUID correlationalId;
    private OrderItemRequest[] items;

}

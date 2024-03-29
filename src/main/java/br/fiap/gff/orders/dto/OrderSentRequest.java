package br.fiap.gff.orders.dto;

import java.util.UUID;

public record OrderSentRequest(Long customerId, UUID transactionId, Item[] items) {
    public record Item(Long productId, Integer quantity) {
    }
}

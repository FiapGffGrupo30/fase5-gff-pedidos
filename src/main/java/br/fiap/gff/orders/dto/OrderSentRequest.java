package br.fiap.gff.orders.dto;

import java.util.UUID;

public record OrderSentRequest(Long customerId, UUID correlationalId, Item[] items) {
    public record Item(String productId, Integer quantity) {
    }
}

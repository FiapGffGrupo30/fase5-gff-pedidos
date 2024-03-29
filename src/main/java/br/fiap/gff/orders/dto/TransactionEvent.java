package br.fiap.gff.orders.dto;

import lombok.Builder;

import java.util.UUID;

@Builder(toBuilder = true)
public record TransactionEvent(Long customerId, UUID transactionId, Item[] items, String status) {
    public record Item(Long productId, Integer quantity) {
    }
}

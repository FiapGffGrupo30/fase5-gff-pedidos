package br.fiap.gff.orders.dto;

import lombok.Builder;

@Builder
public record TransactionProperties(Long customerId) {

}

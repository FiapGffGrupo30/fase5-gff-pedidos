package br.fiap.gff.orders.exception;

import java.util.UUID;

public class OrderNotFoundByCorrelationalIdException extends DomainException {
    public OrderNotFoundByCorrelationalIdException(UUID correlationalId) {
        super(String.format("Order with correlationalId %s not found", correlationalId.toString()));
    }
}

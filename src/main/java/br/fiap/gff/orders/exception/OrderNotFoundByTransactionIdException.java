package br.fiap.gff.orders.exception;

import br.fiap.gff.orders.exception.base.DomainException;
import br.fiap.gff.orders.exception.base.ErrorTypes;

import java.util.UUID;

public class OrderNotFoundByTransactionIdException extends DomainException {
    public OrderNotFoundByTransactionIdException(UUID correlationalId) {
        super(String.format("Order with correlationalId %s not found", correlationalId.toString()));
        this.errorType = ErrorTypes.NOT_FOUND;
    }
}

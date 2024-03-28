package br.fiap.gff.orders.exception;

import br.fiap.gff.orders.exception.base.DomainException;
import br.fiap.gff.orders.exception.base.ErrorTypes;

public class OrderNotFoundByIdException extends DomainException {
    public OrderNotFoundByIdException(Long id) {
        super(String.format("Order with id %s not found", id));
        this.errorType = ErrorTypes.NOT_FOUND;
    }
}

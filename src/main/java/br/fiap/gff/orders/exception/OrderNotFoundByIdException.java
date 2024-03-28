package br.fiap.gff.orders.exception;

import br.fiap.gff.orders.exception.base.DomainException;

public class OrderNotFoundByIdException extends DomainException {
    public OrderNotFoundByIdException(Long id) {
        super(String.format("Order with id %s not found", id));
    }
}

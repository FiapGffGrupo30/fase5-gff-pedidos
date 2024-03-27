package br.fiap.gff.orders.exception;

public class OrderNotFoundByIdException extends DomainException {
    public OrderNotFoundByIdException(Long id) {
        super(String.format("Order with id %s not found", id));
    }
}

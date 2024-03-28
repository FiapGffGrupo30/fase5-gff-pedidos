package br.fiap.gff.orders.exception;

import br.fiap.gff.orders.exception.base.DomainException;

public class ProductNotFoundException extends DomainException {
    public ProductNotFoundException(Long id) {
        super(String.format("Product with id %d not found", id));
    }
}

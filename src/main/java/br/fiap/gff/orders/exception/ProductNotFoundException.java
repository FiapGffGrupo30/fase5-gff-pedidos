package br.fiap.gff.orders.exception;

import br.fiap.gff.orders.exception.base.DomainException;
import br.fiap.gff.orders.exception.base.ErrorTypes;

public class ProductNotFoundException extends DomainException {
    public ProductNotFoundException(Long id) {
        super(String.format("Product with id %d not found", id));
        this.errorType = ErrorTypes.NOT_FOUND;
    }
}

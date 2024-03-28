package br.fiap.gff.orders.exception;

import br.fiap.gff.orders.exception.base.DomainException;
import br.fiap.gff.orders.exception.base.ErrorTypes;

public class CategoryNotFoundException extends DomainException {
    public CategoryNotFoundException(Long id) {
        super(String.format("Category with id %d not found", id));
        this.errorType = ErrorTypes.NOT_FOUND;
    }
}

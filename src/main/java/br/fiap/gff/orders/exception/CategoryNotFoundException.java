package br.fiap.gff.orders.exception;

public class CategoryNotFoundException extends DomainException {
    public CategoryNotFoundException(Long id) {
        super(String.format("Category with id %d not found", id));
    }
}

package br.fiap.gff.orders.exception;

public class ProductNotFoundException extends DomainException {
    public ProductNotFoundException(Long id) {
        super(String.format("Product with id %d not found", id));
    }
}

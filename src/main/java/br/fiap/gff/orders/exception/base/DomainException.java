package br.fiap.gff.orders.exception.base;

import lombok.Getter;

@Getter
public class DomainException extends RuntimeException {

    protected ErrorTypes errorType;

    public DomainException(String message) {
        super(message);
    }
}

package br.fiap.gff.orders.usecases;

import java.util.UUID;

public interface TransactionUseCase {

    void verify(UUID transactionId, Long customerId);
}

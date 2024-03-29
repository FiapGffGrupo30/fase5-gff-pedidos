package br.fiap.gff.orders.usecases;

import br.fiap.gff.orders.dto.TransactionEvent;
import br.fiap.gff.orders.models.Order;

import java.util.List;
import java.util.UUID;

public interface OrderUseCase {

    void create(TransactionEvent event);

    Order filterById(Long id);

    Order filterByTransactionId(UUID transactionId);

    List<Order> getAll();

    Order save(Order order);

    Order update(Long id, String status);

    void update(UUID transactionId, String status);

    void deleteById(Long id);

}

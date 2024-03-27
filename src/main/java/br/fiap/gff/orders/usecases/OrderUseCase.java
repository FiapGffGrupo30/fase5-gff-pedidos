package br.fiap.gff.orders.usecases;

import br.fiap.gff.orders.models.Order;

import java.util.List;
import java.util.UUID;

public interface OrderUseCase {

    Order filterById(Long id);

    Order filterByCorrelationalId(UUID correlationalId);

    List<Order> getAll();

    Order save(Order order);

    Order update(Long id, String status);

    void delete(Long id);

}
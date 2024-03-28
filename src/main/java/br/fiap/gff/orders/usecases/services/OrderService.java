package br.fiap.gff.orders.usecases.services;

import br.fiap.gff.orders.exception.OrderNotFoundByTransactionIdException;
import br.fiap.gff.orders.exception.OrderNotFoundByIdException;
import br.fiap.gff.orders.models.Order;
import br.fiap.gff.orders.repository.OrderRepository;
import br.fiap.gff.orders.usecases.OrderUseCase;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
@RequiredArgsConstructor
public class OrderService implements OrderUseCase {

    private final OrderRepository repository;

    @Override
    public Order filterById(Long id) {
        return repository.findByIdOptional(id).orElseThrow(() -> new OrderNotFoundByIdException(id));
    }

    @Override
    public Order filterByTransactionId(UUID transactionId) {
        Order o = repository.find("transactionId", transactionId).firstResult();
        if (o == null) throw new OrderNotFoundByTransactionIdException(transactionId);
        return o;
    }

    @Override
    public List<Order> getAll() {
        return repository.listAll();
    }

    @Override
    public Order save(Order order) {
        repository.persist(order);
        return order;
    }

    @Override
    public Order update(Long id, String status) {
        Order orderToUpdate = filterById(id);
        Order orderUpdated = orderToUpdate.toBuilder().status(status).build();
        repository.persist(orderUpdated);
        return orderUpdated;
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}

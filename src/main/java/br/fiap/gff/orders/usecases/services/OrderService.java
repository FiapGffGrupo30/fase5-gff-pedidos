package br.fiap.gff.orders.usecases.services;

import br.fiap.gff.orders.dto.OrderSentRequest;
import br.fiap.gff.orders.exception.OrderNotFoundByIdException;
import br.fiap.gff.orders.exception.OrderNotFoundByTransactionIdException;
import br.fiap.gff.orders.exception.base.DomainException;
import br.fiap.gff.orders.models.Order;
import br.fiap.gff.orders.models.OrderItem;
import br.fiap.gff.orders.models.Product;
import br.fiap.gff.orders.repository.OrderRepository;
import br.fiap.gff.orders.usecases.OrderUseCase;
import br.fiap.gff.orders.usecases.ProductUseCase;
import br.fiap.gff.orders.usecases.TransactionUseCase;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
@RequiredArgsConstructor
public class OrderService implements OrderUseCase {

    private final OrderRepository repository;
    private final TransactionUseCase transaction;
    private final ProductUseCase product;

    @Override
    @Transactional
    public void create(OrderSentRequest request) {
        // verify integrity of the request
        transaction.verify(request.transactionId(), request.customerId());
        if (verityTransactionIdempotency(request)) return;

        // Creating the list of ordered products
        List<OrderItem> orderedProducts = getOrderItems(request);

        // Creating order
        Order order = Order.builder()
                .transactionId(request.transactionId())
                .customerId(request.customerId())
                .items(orderedProducts)
                .status("CREATED")
                .build();

        repository.persist(order);
        Log.info(String.format("Order %s created for Transaction %s", order.getId(), order.getTransactionId()));
    }

    private boolean verityTransactionIdempotency(OrderSentRequest request) {
        if (repository.find("transactionId", request.transactionId()).firstResult() != null) {
            Log.info(String.format("Transaction %s already processed", request.transactionId()));
            return true;
        }
        return false;
    }

    private List<OrderItem> getOrderItems(OrderSentRequest request) {
        // Verification of the products in stock and making the product List
        List<OrderItem> orderedProducts = new ArrayList<>();

        for (OrderSentRequest.Item item : request.items()) {
            Product p = product.filterById(item.productId());
            if (p == null) {
                throw new DomainException(String.format("Product %s not found", item.productId()));
            }
            if (p.getQuantity() < item.quantity()) {
                throw new DomainException(String.format("Product %s not in stock", item.productId()));
            }
            OrderItem orderedItem = OrderItem.builder()
                    .price(p.getPrice())
                    .product(p)
                    .quantity(item.quantity())
                    .build();
            orderedProducts.add(orderedItem);

            // Update product stock
            p = p.toBuilder().quantity(p.getQuantity() - item.quantity()).build();
            product.update(item.productId(), p);
        }

        return orderedProducts;
    }

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

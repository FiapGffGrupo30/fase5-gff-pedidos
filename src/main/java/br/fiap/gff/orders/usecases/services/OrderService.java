package br.fiap.gff.orders.usecases.services;

import br.fiap.gff.orders.application.events.CustomerNotifyEvent;
import br.fiap.gff.orders.application.events.PaymentSendEvent;
import br.fiap.gff.orders.dto.TransactionEvent;
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
    private final CustomerNotifyEvent notifyCustomer;
    private final PaymentSendEvent paymentEvent;

    @Override
    @Transactional
    public void create(TransactionEvent event) {
        // verify integrity of the request
        transaction.verify(event.transactionId(), event.customerId());
        if (verityTransactionIdempotency(event)) return;

        // Creating the list of ordered products
        List<OrderItem> orderedProducts = getOrderItems(event);

        double totalOrder = orderedProducts.stream().reduce(0d, (a, b) ->
                a + (b.getPrice() * b.getQuantity()), Double::sum);

        // Creating order
        Order order = createOrderFromEvent(event, orderedProducts, totalOrder);

        repository.persist(order);
        Log.info(String.format("[EVENT - CREATE] Order %s created for Transaction %s", order.getId(), order.getTransactionId()));

        // Notifing customer about the order
        event = event.toBuilder()
                .orderId(order.getId())
                .orderPrice(order.getTotal()).
                status("PENDING PAYMENT").build();

        // Notifing customer and payment services
        broadcastingEvents(event);
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
    public void update(UUID transactionId, String status) {
        Order orderToUpdate = filterByTransactionId(transactionId);
        Order orderUpdated = orderToUpdate.toBuilder().status(status).build();
        repository.update("status = ?1 where transactionId = ?2", status, transactionId);
        Log.info(String.format("[EVENT-UPDATE] Order %s updated for Transaction %s",
                orderUpdated.getId(), orderUpdated.getTransactionId()));

    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }


    private void broadcastingEvents(TransactionEvent event) {
        paymentEvent.send(event);
        notifyCustomer.send(event);
    }

    private static Order createOrderFromEvent(TransactionEvent event, List<OrderItem> orderedProducts, double totalOrder) {
        return Order.builder()
                .transactionId(event.transactionId())
                .customerId(event.customerId())
                .items(orderedProducts)
                .total(totalOrder)
                .status("CREATED")
                .build();
    }

    private boolean verityTransactionIdempotency(TransactionEvent request) {
        if (repository.find("transactionId", request.transactionId()).firstResult() != null) {
            Log.info(String.format("Transaction %s already processed", request.transactionId()));
            return true;
        }
        return false;
    }

    private List<OrderItem> getOrderItems(TransactionEvent request) {
        // Verification of the products in stock and making the product List
        List<OrderItem> orderedProducts = new ArrayList<>();
        for (TransactionEvent.Item item : request.items()) {
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
}

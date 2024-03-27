package br.fiap.gff.orders.repository;

import br.fiap.gff.orders.models.Order;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OrderRepository implements PanacheRepository<Order> {
}

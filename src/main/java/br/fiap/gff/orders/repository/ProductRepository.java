package br.fiap.gff.orders.repository;

import br.fiap.gff.orders.models.Product;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProductRepository implements PanacheRepository<Product> {
}

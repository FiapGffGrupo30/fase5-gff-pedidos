package br.fiap.gff.orders.repository;

import br.fiap.gff.orders.models.Category;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CategoryRepository implements PanacheRepository<Category> {
}

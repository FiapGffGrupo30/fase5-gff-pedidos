package br.fiap.gff.orders.usecases.services;

import br.fiap.gff.orders.exception.ProductNotFoundException;
import br.fiap.gff.orders.models.Category;
import br.fiap.gff.orders.models.Product;
import br.fiap.gff.orders.repository.ProductRepository;
import br.fiap.gff.orders.usecases.CategoryUseCase;
import br.fiap.gff.orders.usecases.ProductUseCase;
import br.fiap.gff.orders.util.Coalesce;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;

@ApplicationScoped
@RequiredArgsConstructor
public class ProductService implements ProductUseCase {

    private final ProductRepository repository;
    private final CategoryUseCase category;

    @Override
    public Product filterById(Long id) {
        return repository.findByIdOptional(id).orElseThrow(() -> new ProductNotFoundException(id));
    }

    @Override
    public List<Product> getAll() {
        return repository.listAll();
    }

    @Override
    public Product save(Product product) {
        Category c = category.filterById(product.getCategory().getId());
        product.setCategory(c);
        repository.persist(product);
        return product;
    }

    @Override
    public Product update(Long id, Product product) {
        Product productToUpdate = filterById(id);
        String name = Coalesce.of(product.getName(), productToUpdate.getName());
        String description = Coalesce.of(product.getDescription(), productToUpdate.getDescription());
        Integer quantity = Coalesce.of(product.getQuantity(), productToUpdate.getQuantity());
        Double price = Coalesce.of(product.getPrice(), productToUpdate.getPrice());
        Product productUpdated = productToUpdate.toBuilder()
                .name(name)
                .description(description)
                .quantity(quantity)
                .price(price)
                .build();
        repository.getEntityManager().merge(productUpdated);
        return productUpdated;
    }


    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}

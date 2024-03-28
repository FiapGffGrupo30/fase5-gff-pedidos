package br.fiap.gff.orders.usecases;

import br.fiap.gff.orders.models.Product;

import java.util.List;

public interface ProductUseCase {

    Product filterById(Long id);

    List<Product> getAll();

    Product save(Product product);

    Product update(Long id, Product product);

    void deleteById(Long id);

}

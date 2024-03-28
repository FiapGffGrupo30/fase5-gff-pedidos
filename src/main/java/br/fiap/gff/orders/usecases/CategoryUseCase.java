package br.fiap.gff.orders.usecases;

import br.fiap.gff.orders.models.Category;

import java.util.List;

public interface CategoryUseCase {

    Category filterById(Long id);

    List<Category> getAll();

    Category save(Category category);

    Category update(Long id, Category category);

    void deleteById(Long id);


}

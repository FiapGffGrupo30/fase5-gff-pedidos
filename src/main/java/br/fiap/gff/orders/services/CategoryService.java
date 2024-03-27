package br.fiap.gff.orders.services;

import br.fiap.gff.orders.exception.CategoryNotFoundException;
import br.fiap.gff.orders.models.Category;
import br.fiap.gff.orders.repository.CategoryRepository;
import br.fiap.gff.orders.usecases.CategoryUseCase;
import br.fiap.gff.orders.util.Coalesce;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

import java.util.List;

@ApplicationScoped
@RequiredArgsConstructor
public class CategoryService implements CategoryUseCase {

    private final CategoryRepository repository;

    @Override
    public Category filterById(Long id) {
        return repository.findByIdOptional(id).orElseThrow(() -> new CategoryNotFoundException(id));
    }

    @Override
    public List<Category> getAll() {
        return repository.listAll();
    }

    @Override
    public Category save(Category category) {
        repository.persist(category);
        return category;
    }

    @Override
    public Category update(Long id, Category category) {
        Category categoryToUpdate = filterById(id);
        String name = Coalesce.of(category.getName(), categoryToUpdate.getName());
        String description = Coalesce.of(category.getDescription(), categoryToUpdate.getDescription());
        Category categoryUpdated = categoryToUpdate.toBuilder().name(name).description(description).build();
        repository.persist(categoryUpdated);
        return categoryUpdated;
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}

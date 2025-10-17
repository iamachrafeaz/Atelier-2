package ma.fstt.shoplite.services;

import jakarta.inject.Inject;
import ma.fstt.shoplite.entities.Category;
import ma.fstt.shoplite.repositories.interfaces.ICategoryRepository;

import java.util.List;

public class CategoryService {
    @Inject
    private ICategoryRepository categoryRepository;

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category findByIdWithProducts(Long id) {
        return categoryRepository.findByIdWithProducts(id);
    }
}

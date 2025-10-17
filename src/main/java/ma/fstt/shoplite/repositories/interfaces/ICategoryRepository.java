package ma.fstt.shoplite.repositories.interfaces;

import ma.fstt.shoplite.entities.Category;

import java.util.List;

public interface ICategoryRepository extends IRepository<Category> {
    Category findById(Long id);
    List<Category> findAll();
    Category findByIdWithProducts(Long id);
}

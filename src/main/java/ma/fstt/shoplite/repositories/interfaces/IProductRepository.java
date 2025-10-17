package ma.fstt.shoplite.repositories.interfaces;

import ma.fstt.shoplite.entities.Product;

import java.util.List;

public interface IProductRepository extends IRepository<Product> {
    void save(Product product);
    Product findById(Long id);
    List<Product> findAll();
    void update(Product product);
    List<Product> searchByLabel(String label);
}

package ma.fstt.shoplite.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import ma.fstt.shoplite.entities.Product;
import ma.fstt.shoplite.repositories.interfaces.IProductRepository;

import java.util.List;

@Named
@ApplicationScoped
public class ProductService {
    @Inject
    IProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public List<Product> searchByLabel(String label) {
        return productRepository.searchByLabel(label);
    }

    public Product findProductById(Long id) {
        return productRepository.findById(id);
    }
}

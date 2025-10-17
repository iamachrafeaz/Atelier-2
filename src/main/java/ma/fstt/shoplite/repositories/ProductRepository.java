package ma.fstt.shoplite.repositories;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ma.fstt.shoplite.configs.EntityManagerSingleton;
import ma.fstt.shoplite.entities.Product;
import ma.fstt.shoplite.repositories.interfaces.IProductRepository;

import java.util.List;

@Named
@ApplicationScoped
@NoArgsConstructor
@AllArgsConstructor
public class ProductRepository implements IProductRepository {

    @Inject
    EntityManagerSingleton ems;


    @Override
    public void save(Product product) {
        EntityManager em = ems.getEntityManager();
        try{
            em.persist(product);
        }finally {
            em.close();
        }
    }

    @Override
    public Product findById(Long id) {
        EntityManager em = ems.getEntityManager();
        try{
            return em.find(Product.class, id);
        }finally {
            em.close();
        }
    }

    @Override
    public List<Product> findAll() {
        EntityManager em = ems.getEntityManager();
        try{
            Query query= em.createQuery("SELECT p FROM Product p", Product.class);
            return query.getResultList();
        }finally {
            em.close();
        }
    }

    @Override
    public void update(Product product) {
        EntityManager em = ems.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(product);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Product> searchByLabel(String label) {
        EntityManager em = ems.getEntityManager();
        try {
            Query query = em.createQuery("SELECT p FROM Product p WHERE p.label LIKE  :label", Product.class);
            query.setParameter("label", "%"+label+"%");
            return query.getResultList();
        }finally {
            em.close();
        }
    }
}

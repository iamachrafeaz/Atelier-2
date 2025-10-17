package ma.fstt.shoplite.repositories;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import ma.fstt.shoplite.configs.EntityManagerSingleton;
import ma.fstt.shoplite.entities.Category;
import ma.fstt.shoplite.repositories.interfaces.ICategoryRepository;

import java.util.List;

public class CategoryRepository implements ICategoryRepository {

    @Inject
    private EntityManagerSingleton ems;

    @Override
    public Category findById(Long id) {
        return null;
    }

    @Override
    public List<Category> findAll() {
        EntityManager em = ems.getEntityManager();
        try{
            Query query = em.createQuery("select c from Category c");
            return query.getResultList();
        }finally {
            em.close();
        }
    }

    public Category findByIdWithProducts(Long id) {
        EntityManager em = ems.getEntityManager();
        try {
            return em.createQuery(
                            "SELECT c FROM Category c LEFT JOIN FETCH c.products WHERE c.id = :id",
                            Category.class
                    )
                    .setParameter("id", id)
                    .getSingleResult();
        } finally {
            em.close();
        }
    }
}

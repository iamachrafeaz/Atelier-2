package ma.fstt.shoplite.repositories;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import ma.fstt.shoplite.configs.EntityManagerSingleton;
import ma.fstt.shoplite.entities.Cart;
import ma.fstt.shoplite.repositories.interfaces.ICartRepository;


@Named
@ApplicationScoped
public class CartRepository implements ICartRepository {

    @Inject
    EntityManagerSingleton ems;

    @Override
    public Long save(Cart object) {
        EntityManager em = ems.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Cart managedCart = em.merge(object);
            tx.commit();
            return managedCart.getId();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public Cart findByUserId(Long id) {
        EntityManager em = ems.getEntityManager();
        return em.createQuery("SELECT c FROM Cart c WHERE c.user.id = :id", Cart.class)
                .setParameter("id", id)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public void delete(Cart object) {
        EntityManager em = ems.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Cart managedCart = em.find(Cart.class, object.getId());
            if (managedCart != null) {
                em.remove(managedCart);
            }

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        }
    }
}

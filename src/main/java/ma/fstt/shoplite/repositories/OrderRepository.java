package ma.fstt.shoplite.repositories;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ma.fstt.shoplite.configs.EntityManagerSingleton;
import ma.fstt.shoplite.entities.Order;
import ma.fstt.shoplite.repositories.interfaces.IOrderRepository;

import java.util.List;

@Named
@ApplicationScoped
public class OrderRepository implements IOrderRepository {

    @Inject
    EntityManagerSingleton ems;

    @Override
    public void save(Order object) {
        EntityManager em = ems.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(object);
            em.getTransaction().commit();
        }catch (Exception e){
            em.getTransaction().rollback();
        }finally {
            em.close();
        }
    }

    @Override
    public void deleteById(Long id) {
        EntityManager em = ems.getEntityManager();
        try {
            em.getTransaction().begin();
            em.remove(em.find(Order.class, id));
            em.getTransaction().commit();
        }catch (Exception e){
            em.getTransaction().rollback();
        }finally {
            em.close();
        }
    }

    @Override
    public List<Order> findByUserId(Long id) {
        return ems.getEntityManager().createQuery("select o FROM Order o WHERE o.user.id = :userId").setParameter("userId", id).getResultList();
    }

    @Override
    public Order findById(Long id) {
        return ems.getEntityManager().find(Order.class, id);
    }
}

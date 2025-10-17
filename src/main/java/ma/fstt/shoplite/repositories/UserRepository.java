package ma.fstt.shoplite.repositories;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import ma.fstt.shoplite.configs.EntityManagerSingleton;
import ma.fstt.shoplite.entities.User;
import ma.fstt.shoplite.repositories.interfaces.IUserRepository;

import java.util.List;

@ApplicationScoped
@Named
public class UserRepository implements IUserRepository {

    @Inject
    EntityManagerSingleton ems;

    public void save(User user) {
        EntityManager em = ems.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            if (user.getId() == null)
                em.persist(user);
            else
                em.merge(user);

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException(e);
        } finally {
            em.close();
        }
    }


    public void deleteById(Long id) {
        EntityManager em = ems.getEntityManager();
        User c = em.find(User.class, id);
        if (c != null) em.remove(c);
    }

    public User findByEmail(String email) {
        EntityManager em = ems.getEntityManager();
        return em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                .setParameter("email", email)
                .getSingleResult();
    }

    public List<User> findAll() {
        EntityManager em = ems.getEntityManager();
        return em.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    public Boolean existsByEmail(String email) {
        EntityManager em = ems.getEntityManager();

        List<User> users = em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                .setParameter("email", email)
                .getResultList();
        return !users.isEmpty();
    }
}

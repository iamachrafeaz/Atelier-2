package ma.fstt.shoplite.configs;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import ma.fstt.shoplite.entities.Product;

@Named
@ApplicationScoped
public class EntityManagerSingleton {
    private EntityManagerFactory emf;

    public EntityManagerSingleton() {
        emf = Persistence.createEntityManagerFactory("default");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

}


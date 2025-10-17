package ma.fstt.shoplite.repositories.interfaces;

import ma.fstt.shoplite.entities.User;

import java.util.List;

public interface IUserRepository extends IRepository<User> {
    void save(User object);
    void deleteById(Long id);
    User findByEmail(String email);
    List<User> findAll();
    Boolean existsByEmail(String email);
}

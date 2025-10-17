package ma.fstt.shoplite.repositories.interfaces;

import ma.fstt.shoplite.entities.Cart;

public interface ICartRepository extends IRepository<Cart> {
    Long save(Cart object);
    Cart findByUserId(Long id);
    void delete(Cart object);
}

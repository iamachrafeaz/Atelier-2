package ma.fstt.shoplite.repositories.interfaces;

import ma.fstt.shoplite.entities.Order;

import java.util.List;

public interface IOrderRepository extends IRepository<Order>{
    void save(Order object);
    void deleteById(Long id);
    List<Order> findByUserId(Long id);
    Order findById(Long id);
}

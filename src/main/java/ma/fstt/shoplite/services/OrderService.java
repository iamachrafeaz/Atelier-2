package ma.fstt.shoplite.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import ma.fstt.shoplite.entities.Cart;
import ma.fstt.shoplite.entities.CartItem;
import ma.fstt.shoplite.entities.Order;
import ma.fstt.shoplite.entities.OrderItem;
import ma.fstt.shoplite.enums.OrderStatusEnum;
import ma.fstt.shoplite.repositories.interfaces.ICartRepository;
import ma.fstt.shoplite.repositories.interfaces.IOrderRepository;

import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Named
@ApplicationScoped
public class OrderService {
    @Inject
    IOrderRepository orderRepository;

    @Inject
    ICartRepository cartRepository;

    public void saveOrder(Long userId)
    {
        Order order = new Order();
        Set<OrderItem> orderItems = new HashSet<>();

        Cart cart = cartRepository.findByUserId(userId);

        for (CartItem item : cart.getCartItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(item.getProduct());
            orderItem.setQuantity(item.getQuantity());

            orderItems.add(orderItem);
            orderItem.setOrder(order);
        }

        order.setStatus(OrderStatusEnum.PENDING);
        order.setOrderItems(orderItems);
        order.setCreatedAt(new Date(System.currentTimeMillis()));
        order.setUser(cart.getUser());
        double total = orderItems.stream()
                .mapToDouble(oi -> oi.getQuantity() * oi.getProduct().getPrice())
                .sum();
        order.setTotal(total);

        orderRepository.save(order);

        cartRepository.delete(cart);
    }

    public List<Order> listOrdersByUserId(Long userId)
    {
        return orderRepository.findByUserId(userId);
    }

    public Order showOrderById(Long orderId)
    {
        return orderRepository.findById(orderId);
    }
}

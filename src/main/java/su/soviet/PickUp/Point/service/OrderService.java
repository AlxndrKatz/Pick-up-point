package su.soviet.PickUp.Point.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import su.soviet.PickUp.Point.dao.OrderRepository;
import su.soviet.PickUp.Point.model.Order;
import su.soviet.PickUp.Point.model.OrderStatus;
import su.soviet.PickUp.Point.model.User;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private UserService userService;

    public Order createOrder(Order order) {
        if (order.getName() != null || order.getCustomer() != null) {
            order.setStatus(OrderStatus.RECEIVED);
            return orderRepo.save(order);
        }
        return null;
    }

    public Order getOrderById(Long orderId) {
        return orderRepo.findById(orderId).orElse(null);
    }

    public Order updateOrderStatus(Long orderId, OrderStatus newStatus) {
        Order order = orderRepo.findById(orderId).orElse(null);
        if (order == null) {
            return null;
        }

        OrderStatus currentStatus = order.getStatus();
        if ((currentStatus == OrderStatus.RECEIVED && newStatus == OrderStatus.PICKED_UP)
                || (currentStatus == OrderStatus.RECEIVED && newStatus == OrderStatus.RETURN)){
            order.setStatus(newStatus);
            return orderRepo.save(order);
        }
        return null;
    }

    public Set<Order> getAllOrders() {//EMPLOYEE
        return new HashSet<>(orderRepo.findAll());
    }

    public boolean validateQR(String qr) {
        Long userId = Long.valueOf(qr);
        User user = userService.getUserById(userId);
        return user != null;
    }

    public Set<Order> getUserOrders(Long userId) {
        User user = userService.getUserById(userId);
        return user.getOrders()
                .stream().filter(order -> order.getStatus() == OrderStatus.RECEIVED)
                .collect(Collectors.toSet());
    }

    public Boolean checkUserOrders(Long userId) {
        User user = userService.getUserById(userId);
        if (!(user.getOrders().isEmpty())) {
            return true;
        } else {
            return false;
        }
    }
}
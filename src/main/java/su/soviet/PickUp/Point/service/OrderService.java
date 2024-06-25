package su.soviet.PickUp.Point.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import su.soviet.PickUp.Point.dao.OrderRepository;
import su.soviet.PickUp.Point.model.Order;
import su.soviet.PickUp.Point.model.OrderStatus;

import java.util.HashSet;
import java.util.Set;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepo;

    public Order createOrder(Order order) {
        order.setStatus(OrderStatus.CREATED);
        order.setLink(null);// эррормаппинг закинуть на этот случай
        return orderRepo.save(order);
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
        if ((currentStatus == OrderStatus.CREATED && newStatus == OrderStatus.RECEIVED) ||
                (currentStatus == OrderStatus.RECEIVED && (newStatus == OrderStatus.PICKED_UP || newStatus == OrderStatus.RETURN))) {
            order.setStatus(newStatus);
            if (newStatus == OrderStatus.RECEIVED) {
                order.setLink(generateLink(order.getCustomer().getId(), orderId));
            } else {
                order.setLink(null);
            }
            return orderRepo.save(order);
        }
        return null;
    }

    public String generateLink(Long userId, Long orderId) {
        return "протокол+адрес/order?userId=" + userId + "&orderId=" + orderId;
    }

    public Set<Order> getAllOrders() {
        return new HashSet<>(orderRepo.findAll());
    }
}

package su.soviet.PickUp.Point.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import su.soviet.PickUp.Point.dao.OrderRepository;
import su.soviet.PickUp.Point.model.Order;
import su.soviet.PickUp.Point.model.OrderStatus;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepo;

    public Order createOrder(Order order) {
        order.setStatus(OrderStatus.CREATED);
        order.setLink(null);// эррормаппинг закинуть на этот случай
        return orderRepo.save(order);
    }

    public Order updateOrderStatus(Long orderId, OrderStatus status) {
        Order order = orderRepo.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        order.setLink(status == OrderStatus.RECEIVED ? generateLink(order.getCustomer().getId(), orderId) : null);
        order.setStatus(status);
        return orderRepo.save(order);
    }

    public String generateLink(Long userId, Long orderId) {
        return "протокол+адрес/order?userId=" + userId + "&orderId=" + orderId;
    }
}

package su.soviet.PickUp.Point.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import su.soviet.PickUp.Point.dao.OrderRepository;
import su.soviet.PickUp.Point.dto.CustomerDTO;
import su.soviet.PickUp.Point.dto.OrderDTO;
import su.soviet.PickUp.Point.dto.UserDTO;
import su.soviet.PickUp.Point.model.Order;
import su.soviet.PickUp.Point.model.OrderStatus;
import su.soviet.PickUp.Point.model.User;

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

    public OrderDTO getOrderById(Long orderId) {
        Order order = orderRepo.findById(orderId).orElse(null);
        if (order == null) {
            return null;
        }
        return mapToOrderDTO(order);
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

    public Set<OrderDTO> getAllOrders() {
        return orderRepo.findAll().stream().map(this::mapToOrderDTO).collect(Collectors.toSet());
    }

    public boolean validateQR(String qr) {
        Long userId = Long.valueOf(qr);
        User user = userService.getUserById(userId);
        return user != null;
    }

    public Set<OrderDTO> getUserOrders(Long userId) {
        CustomerDTO dto = userService.mapToCustomerDTO(userService.getUserById(userId));
        return dto.getOrders()
                .stream()
                .filter(order -> order.getStatus() == OrderStatus.RECEIVED)
                .map(this::mapToOrderDTO).collect(Collectors.toSet());
    }

    public Boolean checkUserOrders(Long userId) {
        return !(getUserOrders(userId).isEmpty());
    }

    private OrderDTO mapToOrderDTO (Order order) {
        return new OrderDTO(order.getId(), order.getName(), order.getStatus(), order.getCustomer().getId());
    }
}
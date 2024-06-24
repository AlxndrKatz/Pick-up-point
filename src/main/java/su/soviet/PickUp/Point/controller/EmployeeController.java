package su.soviet.PickUp.Point.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import su.soviet.PickUp.Point.model.Order;
import su.soviet.PickUp.Point.model.OrderStatus;
import su.soviet.PickUp.Point.service.OrderService;

import java.util.Set;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

    @Autowired
    private OrderService orderService;

    @PreAuthorize("hasAuthority('ROLE_EMPLOYEE')")
    @PutMapping("employee/orders/{orderId}/status")//СТАТУС ПЕРЕДАВАТЬ АППЕРКЕЙСОМ, ИНАЧЕ НЕ РАЗБЕРЕТ!!! api/v1/employee/orders/123/status?newStatus=PICKED_UP"
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long orderId, @RequestParam OrderStatus newStatus) {
        Order updatedOrder = orderService.updateOrderStatus(orderId, newStatus);
        if (updatedOrder == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
    }

    //ТЕСТОВЫЙ МЕТОД - ПОТОМ УБРАТЬ
    @PreAuthorize("hasAuthority('ROLE_EMPLOYEE')")
    @GetMapping("/employee/orders")
    public ResponseEntity<Set<Order>> getAllOrders() {
        Set<Order> orders = orderService.getAllOrders();
        if (orders == null || orders.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_EMPLOYEE')")
    @GetMapping("/employee/orders/{orderId}")
    public ResponseEntity<Order> getOrder(@PathVariable Long orderId) {
        Order order = orderService.getOrderById(orderId);
        if (order == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(order, HttpStatus.OK);
    }
    //

    @PreAuthorize("hasAuthority('ROLE_EMPLOYEE')")
    @GetMapping("/employee/")
    public ModelAndView getDefaultPage() {
        return new ModelAndView("employee");
    }
}

package su.soviet.PickUp.Point.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import su.soviet.PickUp.Point.dto.OrderDTO;
import su.soviet.PickUp.Point.model.Order;
import su.soviet.PickUp.Point.model.OrderStatus;
import su.soviet.PickUp.Point.service.OrderService;
import su.soviet.PickUp.Point.service.QRService;

import java.util.Set;

@RestController
@RequestMapping("/pick-up-point")
public class EmployeeController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private QRService qrService;

    @PreAuthorize("hasAuthority('ROLE_EMPLOYEE')")
    @GetMapping("/employee/")
    public ModelAndView getDefaultPage() {
        return new ModelAndView("order_pick_up");
    }

    @PreAuthorize("hasAuthority('ROLE_EMPLOYEE')")
    @PutMapping("/orders/{orderId}/")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long orderId, @RequestParam OrderStatus newStatus) {
        Order updatedOrder = orderService.updateOrderStatus(orderId, newStatus);
        if (updatedOrder == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_EMPLOYEE')")
    @GetMapping("/orders")
    public ResponseEntity<Set<OrderDTO>> getAllOrders() {
        Set<OrderDTO> orders = orderService.getAllOrders();
        if (orders.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_EMPLOYEE')")
    @PostMapping("/new-order")
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        Order createdOrder = orderService.createOrder(order);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ROLE_EMPLOYEE')")
    @GetMapping("/orders/{userId}")
    public ResponseEntity<Set<OrderDTO>> getUserOrders(@PathVariable(value = "userId") Long userId) {
        Set<OrderDTO> orders = orderService.getUserOrders(userId);
        if (!(orders.isEmpty())) {
           return new ResponseEntity<>(orders, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PreAuthorize("hasAuthority('ROLE_EMPLOYEE')")
    @GetMapping("/get-my-orders/")
    public ModelAndView getUsersOrdersView(@RequestParam(value = "userId") Long userId) {
        Set<OrderDTO> orders = orderService.getUserOrders(userId);

        ModelAndView modelAndView = new ModelAndView("orders_page");
        modelAndView.addObject("orders", orders);
        return modelAndView;
    }
}

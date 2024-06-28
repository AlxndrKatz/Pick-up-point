package su.soviet.PickUp.Point.controller;

import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import su.soviet.PickUp.Point.model.Order;
import su.soviet.PickUp.Point.model.OrderStatus;
import su.soviet.PickUp.Point.model.User;
import su.soviet.PickUp.Point.service.OrderService;
import su.soviet.PickUp.Point.service.QRService;
import su.soviet.PickUp.Point.service.UserService;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private QRService qrService;

    @Autowired
    private OrderService orderService;

    @PreAuthorize("hasAnyAuthority('ROLE_EMPLOYEE', 'ROLE_USER')")
    @GetMapping("/myorders/user/{id}")
    public ResponseEntity<Set<Order>> getUserOrders(@PathVariable(value = "id") Long userId) {
        User user = service.getUserById(userId);
        Set<Order> readyOrders = user.getOrders()
                .stream().filter(order -> order.getStatus()==OrderStatus.RECEIVED)
                .collect(Collectors.toSet());
        return new ResponseEntity<>(readyOrders ,HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/myorders/{orderId}/qr")
    public ResponseEntity<byte[]> getCode(@PathVariable Long orderId) throws IOException, WriterException {
        String link = orderService.getOrderById(orderId).getLink();

        byte[] qrImage = qrService.generateQRCode(link, 200, 200);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(qrImage);
    }


    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/myorders/")
    public ModelAndView getDefaultPage() {
        return new ModelAndView("order_pick_up");
    }
}

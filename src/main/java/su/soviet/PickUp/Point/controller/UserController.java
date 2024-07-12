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
import su.soviet.PickUp.Point.service.OrderService;
import su.soviet.PickUp.Point.service.QRService;
import su.soviet.PickUp.Point.service.UserService;

import java.io.IOException;

@RestController
@RequestMapping("/pick-up-point")
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private QRService qrService;

    @Autowired
    private OrderService orderService;

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/myorders/")
    public ModelAndView getDefaultPage() {
        return new ModelAndView("order_pick_up");
    }

   @PreAuthorize("hasAnyAuthority('ROLE_EMPLOYEE', 'ROLE_USER')")
   @GetMapping("/myorders/user/{id}")
   public ResponseEntity<Boolean> checkUserOrders(@PathVariable(value = "id") Long userId) {
       if (orderService.checkUserOrders(userId)) {
           return new ResponseEntity<>(true, HttpStatus.OK);
       } else {
           return new ResponseEntity<>(false, HttpStatus.OK);
       }
   }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/myorders/{userId}/qr")
    public ResponseEntity<byte[]> getCode(@PathVariable Long userId) throws IOException, WriterException {
        byte[] qrImage = qrService.generateQRCode(userId, 200, 200);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(qrImage);
    }
}

package su.soviet.PickUp.Point.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import su.soviet.PickUp.Point.model.OrderStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Long id;
    private String name;
    private OrderStatus status;
    private Long userId;
}

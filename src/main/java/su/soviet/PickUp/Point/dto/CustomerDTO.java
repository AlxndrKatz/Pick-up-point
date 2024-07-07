package su.soviet.PickUp.Point.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import su.soviet.PickUp.Point.model.Order;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {
    private Long id;
    private Set<Order> orders;
}

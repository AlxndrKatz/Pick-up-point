package su.soviet.PickUp.Point.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import su.soviet.PickUp.Point.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}

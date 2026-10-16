package io.github.ronaldking275.oms.repository;

import io.github.ronaldking275.oms.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface OrderItemRepository extends JpaRepository<OrderItem, UUID> {
}
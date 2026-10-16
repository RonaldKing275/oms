package io.github.ronaldking275.oms.repository;

import io.github.ronaldking275.oms.entity.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, UUID> {
}
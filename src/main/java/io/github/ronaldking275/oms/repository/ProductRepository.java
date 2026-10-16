package io.github.ronaldking275.oms.repository;

import io.github.ronaldking275.oms.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
}
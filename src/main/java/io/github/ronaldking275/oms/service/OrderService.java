package io.github.ronaldking275.oms.service;

import io.github.ronaldking275.oms.dto.OrderRequest;
import io.github.ronaldking275.oms.entity.CustomerOrder;
import io.github.ronaldking275.oms.entity.OrderItem;
import io.github.ronaldking275.oms.entity.OrderStatus;
import io.github.ronaldking275.oms.entity.Product;
import io.github.ronaldking275.oms.exception.BusinessValidationException;
import io.github.ronaldking275.oms.exception.ResourceNotFoundException;
import io.github.ronaldking275.oms.repository.CustomerOrderRepository;
import io.github.ronaldking275.oms.repository.OrderItemRepository;
import io.github.ronaldking275.oms.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final ProductRepository productRepository;
    private final CustomerOrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Transactional
    public CustomerOrder placeOrder(OrderRequest request) {
        CustomerOrder order = CustomerOrder.builder()
                .customerEmail(request.customerEmail())
                .status(OrderStatus.NEW)
                .totalAmount(BigDecimal.ZERO)
                .build();

        order = orderRepository.save(order);
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (var itemReq : request.items()) {
            Product product = productRepository.findById(itemReq.productId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + itemReq.productId()));

            if (product.getStockQuantity() < itemReq.quantity()) {
                throw new BusinessValidationException("Insufficient stock for product: " + product.getName());
            }

            product.setStockQuantity(product.getStockQuantity() - itemReq.quantity());
            productRepository.save(product);

            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .product(product)
                    .quantity(itemReq.quantity())
                    .unitPrice(product.getPrice())
                    .build();
            orderItemRepository.save(orderItem);

            BigDecimal itemTotal = product.getPrice().multiply(BigDecimal.valueOf(itemReq.quantity()));
            totalAmount = totalAmount.add(itemTotal);
        }

        order.setTotalAmount(totalAmount);
        return orderRepository.save(order);
    }

    @Transactional
    public CustomerOrder updateOrderStatus(UUID orderId, OrderStatus newStatus) {
        CustomerOrder order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found: " + orderId));

        order.setStatus(newStatus);
        return orderRepository.save(order);
    }
}
package io.github.ronaldking275.oms.controller;

import io.github.ronaldking275.oms.dto.OrderRequest;
import io.github.ronaldking275.oms.dto.OrderStatusUpdateRequest;
import io.github.ronaldking275.oms.entity.CustomerOrder;
import io.github.ronaldking275.oms.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public CustomerOrder placeOrder(@Valid @RequestBody OrderRequest request) {
        return orderService.placeOrder(request);
    }

    @PatchMapping("/{id}/status")
    public CustomerOrder updateStatus(@PathVariable UUID id, @RequestBody OrderStatusUpdateRequest request) {
        return orderService.updateOrderStatus(id, request.status());
    }
}
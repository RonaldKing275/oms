package io.github.ronaldking275.oms.dto;

import io.github.ronaldking275.oms.entity.OrderStatus;

public record OrderStatusUpdateRequest(OrderStatus status) {
}
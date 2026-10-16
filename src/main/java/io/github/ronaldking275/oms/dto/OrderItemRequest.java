package io.github.ronaldking275.oms.dto;

import java.util.UUID;

public record OrderItemRequest(UUID productId, Integer quantity) {
}
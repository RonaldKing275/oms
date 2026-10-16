package io.github.ronaldking275.oms.dto;

import java.util.List;

public record OrderRequest(String customerEmail, List<OrderItemRequest> items) {
}
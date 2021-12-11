package com.andromeda.foodcare.controller;

import com.andromeda.controller.OrderApi;
import com.andromeda.dto.OrderPayload;
import com.andromeda.dto.OrderResponse;
import com.andromeda.foodcare.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class OrderController implements OrderApi {

    private final OrderService orderService;

    @Override
    public ResponseEntity<OrderResponse> addOrder(OrderPayload orderPayload) {
        return ResponseEntity.ok(orderService.addOrder(orderPayload));
    }

    @Override
    public ResponseEntity<OrderResponse> getOrderByOrderId(Long orderId) {
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }

    @Override
    public ResponseEntity<List<OrderResponse>> getOrdersByBusinessId(Long businessId) {
        return ResponseEntity.ok(orderService.getOrderByBusinessId(businessId));
    }

    @Override
    public ResponseEntity<List<OrderResponse>> getOrdersByUserId(Long userId) {
        return ResponseEntity.ok(orderService.getOrderByUserId(userId));
    }

    @Override
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @Override
    public ResponseEntity<String> deleteOrderById(Long orderId) {
        return ResponseEntity.ok(orderService.deleteOrderById(orderId));
    }
}

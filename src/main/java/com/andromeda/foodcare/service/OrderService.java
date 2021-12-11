package com.andromeda.foodcare.service;

import com.andromeda.dto.OrderPayload;
import com.andromeda.dto.OrderResponse;
import com.andromeda.foodcare.mapper.OrderMapper;
import com.andromeda.foodcare.model.Order;
import com.andromeda.foodcare.repository.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public OrderResponse addOrder(OrderPayload orderPayload) {
        Order order = orderMapper.toOrder(orderPayload);
        orderRepository.save(order);
        return orderMapper.toOrderResponse(order);
    }

    public OrderResponse getOrderById(Long orderId) {
        return orderMapper.toOrderResponse(orderRepository.getAllById(orderId));
    }


    public List<OrderResponse> getOrderByBusinessId(Long businessId) {
        return orderRepository.getAllByBusinessId(businessId)
                .stream().map(orderMapper::toOrderResponse).collect(Collectors.toList());
    }

    public List<OrderResponse> getOrderByUserId(Long userId) {
        return orderRepository.getAllByUserId(userId)
                .stream().map(orderMapper::toOrderResponse).collect(Collectors.toList());
    }

    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll()
                .stream().map(orderMapper::toOrderResponse).collect(Collectors.toList());
    }


    public String deleteOrderById(Long orderId) {
        orderRepository.delete(orderRepository.getById(orderId));
        return "Order deleted";
    }
}

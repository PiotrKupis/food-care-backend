package com.andromeda.foodcare.repository;

import com.andromeda.foodcare.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Order getAllById(Long id);

    List<Order> getAllByUserId(Long userId);

    List<Order> getAllByBusinessId(Long businessId);
}

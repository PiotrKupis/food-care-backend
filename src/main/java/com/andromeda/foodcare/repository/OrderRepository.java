package com.andromeda.foodcare.repository;

import com.andromeda.foodcare.model.Order;
import java.util.List;
import java.util.Optional;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Order getAllById(Long id);

    List<Order> getAllByUserId(Long userId);

    List<Order> getAllByBusinessId(Long businessId);

    List<Order> getAllByProductId(Long productId);

    List<Order> findAll();
}

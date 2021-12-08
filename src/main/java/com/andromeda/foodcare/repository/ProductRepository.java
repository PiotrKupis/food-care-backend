package com.andromeda.foodcare.repository;

import com.andromeda.foodcare.model.Product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> getAllByOwnerId(Long ownerId);

    List<Product> getAllByNameIgnoreCaseContainingAndOwnerId(String name, Long ownerId);
}

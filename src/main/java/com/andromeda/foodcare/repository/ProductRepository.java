package com.andromeda.foodcare.repository;

import com.andromeda.foodcare.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> getAllByOwnerId(Long ownerId);

}

package com.andromeda.foodcare.repository;

import com.andromeda.foodcare.model.Business;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessRepository extends JpaRepository<Business, Long> {

    List<Business> getAllByAddress_City(String city);

    List<Business> findByNameIgnoreCase(String name);
}

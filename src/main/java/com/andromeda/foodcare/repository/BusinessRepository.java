package com.andromeda.foodcare.repository;

import com.andromeda.foodcare.model.Business;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusinessRepository extends JpaRepository<Business, Long> {

    List<Business> getAllByAddress_City(String city);
}

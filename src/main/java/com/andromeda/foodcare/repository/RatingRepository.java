package com.andromeda.foodcare.repository;

import com.andromeda.foodcare.model.Rating;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

    Optional<Rating> findRatingByBusinessIdAndUserId(Long businessId, Long userId);
}

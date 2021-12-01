package com.andromeda.foodcare.service;

import com.andromeda.dto.RatingPayload;
import com.andromeda.foodcare.mapper.RatingMapper;
import com.andromeda.foodcare.model.Rating;
import com.andromeda.foodcare.model.User;
import com.andromeda.foodcare.repository.RatingRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@AllArgsConstructor
@Service
public class RatingService {

    private final RatingRepository ratingRepository;
    private final AuthService authService;
    private final BusinessService businessService;
    private final RatingMapper ratingMapper;

    @Transactional
    public RatingPayload addBusinessRating(RatingPayload ratingPayload) {
        log.info("Adding a new rating");
        businessService.getBusinessById(ratingPayload.getBusinessId());
        User currentUser = authService.getCurrentUser();
        Rating rating = ratingMapper.toRating(ratingPayload, currentUser);

        ratingRepository.findRatingByBusinessIdAndUserId(ratingPayload.getBusinessId(),
            currentUser.getId()).ifPresentOrElse(
            r -> r.setRating(ratingPayload.getRating()),
            () -> ratingRepository.save(rating)
        );

        return ratingMapper.toRatingPayload(rating);
    }
}

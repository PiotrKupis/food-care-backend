package com.andromeda.foodcare.controller;

import com.andromeda.controller.RatingApi;
import com.andromeda.dto.RatingPayload;
import com.andromeda.foodcare.service.RatingService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class RatingController implements RatingApi {

    private final RatingService ratingService;

    @Override
    public ResponseEntity<RatingPayload> addBusinessRating(RatingPayload ratingPayload) {
        return ResponseEntity.ok(ratingService.addBusinessRating(ratingPayload));
    }

    @Override
    public ResponseEntity<RatingPayload> getBusinessRating(Long id) {
        return ResponseEntity.ok(ratingService.getBusinessRating(id));
    }
}

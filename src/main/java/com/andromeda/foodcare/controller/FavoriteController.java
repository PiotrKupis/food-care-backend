package com.andromeda.foodcare.controller;

import com.andromeda.controller.FavoriteApi;
import com.andromeda.dto.BusinessResponse;
import com.andromeda.dto.FavoritePayload;
import com.andromeda.foodcare.service.FavoriteService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class FavoriteController implements FavoriteApi {

    private final FavoriteService favoriteService;

    @Override
    public ResponseEntity<BusinessResponse> addBusinessToFavorites(
        FavoritePayload favoritePayload) {
        return ResponseEntity.ok(favoriteService.addBusinessToFavorites(favoritePayload));
    }
}

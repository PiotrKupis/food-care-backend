package com.andromeda.foodcare.controller;

import com.andromeda.controller.FavoriteApi;
import com.andromeda.dto.BusinessResponse;
import com.andromeda.dto.FavoritePayload;
import com.andromeda.foodcare.service.FavoriteService;
import java.util.List;
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

    @Override
    public ResponseEntity<List<BusinessResponse>> getFavoriteBusinesses() {
        return ResponseEntity.ok(favoriteService.getFavoriteBusinesses());
    }
    @Override
    public ResponseEntity<String> deleteBusinessFromFavorite(Long id) {
        return ResponseEntity.ok(favoriteService.deleteBusinessFromFavorite(id));
    }
}

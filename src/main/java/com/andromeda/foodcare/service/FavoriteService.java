package com.andromeda.foodcare.service;

import com.andromeda.dto.BusinessResponse;
import com.andromeda.dto.FavoritePayload;
import com.andromeda.foodcare.mapper.BusinessMapper;
import com.andromeda.foodcare.model.Business;
import com.andromeda.foodcare.model.User;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@AllArgsConstructor
@Service
public class FavoriteService {

    private final AuthService authService;
    private final BusinessService businessService;
    private final BusinessMapper businessMapper;

    @Transactional
    public BusinessResponse addBusinessToFavorites(FavoritePayload favoritePayload) {
        log.info("Adding business to favorites");
        User currentUser = authService.getCurrentUser();
        Business business = businessService.getBusinessById(favoritePayload.getBusinessId());
        currentUser.getFavorites().add(business);
        return businessMapper.toBusinessResponse(business);
    }

    public List<BusinessResponse> getFavoriteBusinesses() {
        log.info("Getting favorite businesses");
        return authService.getCurrentUser().getFavorites().stream()
                .map(businessMapper::toBusinessResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public String deleteBusinessFromFavorite(Long id) {
        log.info("Deleting business from favorites");
        User currentUser = authService.getCurrentUser();
        Business business = businessService.getBusinessById(id);
        currentUser.getFavorites().remove(business);
        return "Order deleted";
    }
}

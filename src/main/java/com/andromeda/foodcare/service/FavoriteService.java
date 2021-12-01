package com.andromeda.foodcare.service;

import com.andromeda.dto.BusinessResponse;
import com.andromeda.dto.FavoritePayload;
import com.andromeda.foodcare.mapper.BusinessMapper;
import com.andromeda.foodcare.model.Business;
import com.andromeda.foodcare.model.User;
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
}

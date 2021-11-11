package com.andromeda.foodcare.service;

import com.andromeda.dto.BusinessPayload;
import com.andromeda.foodcare.enums.UserRole;
import com.andromeda.foodcare.exceptions.UserException;
import com.andromeda.foodcare.mapper.BusinessMapper;
import com.andromeda.foodcare.model.Business;
import com.andromeda.foodcare.model.User;
import com.andromeda.foodcare.repository.BusinessRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@AllArgsConstructor
@Service
public class BusinessService {

    private final AuthService authService;
    private final BusinessRepository businessRepository;
    private final BusinessMapper businessMapper;

    @Transactional
    public BusinessPayload addBusiness(BusinessPayload businessPayload) {
        Business business = businessMapper.toBusiness(businessPayload);
        log.info("Adding a new business " + business.getName());

        business = businessRepository.save(business);
        User currentUser = authService.getCurrentUser();
        currentUser.setRole(UserRole.BUSINESS);
        currentUser.setBusiness(business);
        return businessMapper.toBusinessPayload(business);
    }

    public BusinessPayload getCurrentUserBusiness() {
        log.info("Getting business of the current user");
        User currentUser = authService.getCurrentUser();
        if (currentUser.getBusiness() == null) {
            throw UserException.userDoesntHaveBusiness();
        }
        return businessMapper.toBusinessPayload(currentUser.getBusiness());
    }
}

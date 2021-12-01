package com.andromeda.foodcare.service;

import com.andromeda.dto.BusinessPayload;
import com.andromeda.dto.BusinessResponse;
import com.andromeda.foodcare.enums.UserRole;
import com.andromeda.foodcare.exceptions.BusinessException;
import com.andromeda.foodcare.exceptions.UserException;
import com.andromeda.foodcare.mapper.BusinessMapper;
import com.andromeda.foodcare.model.Business;
import com.andromeda.foodcare.model.User;
import com.andromeda.foodcare.repository.BusinessRepository;
import java.util.ArrayList;
import java.util.List;
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
    public BusinessResponse addBusiness(BusinessPayload businessPayload) {
        Business business = businessMapper.toBusiness(businessPayload);
        log.info("Adding a new business " + business.getName());

        business = businessRepository.save(business);
        User currentUser = authService.getCurrentUser();
        currentUser.setRole(UserRole.BUSINESS);
        currentUser.setBusiness(business);
        return businessMapper.toBusinessResponse(business);
    }

    public BusinessResponse getCurrentUserBusiness() {
        log.info("Getting business of the current user");
        User currentUser = authService.getCurrentUser();
        if (currentUser.getBusiness() == null) {
            throw UserException.userDoesntHaveBusiness();
        }
        return businessMapper.toBusinessResponse(currentUser.getBusiness());
    }

    public List<BusinessResponse> getAllBusinessesList(String city) {
        log.info("Getting business in selected city");
        List<Business> businessList = businessRepository.getAllByAddress_City(city);

        return getBusinessResponsesList(businessList);
    }

    public List<BusinessResponse> findBusinessByName(String name) {
        log.info("Looking for businesses with name like: " + name);
        List<Business> businessList = businessRepository.findByNameIgnoreCase(name);

        return getBusinessResponsesList(businessList);
    }

    private List<BusinessResponse> getBusinessResponsesList(List<Business> businessList) {
        List<BusinessResponse> businessResponseList = new ArrayList<>();

        for (Business business :
            businessList) {
            businessResponseList.add(businessMapper.toBusinessResponse(business));
        }
        return businessResponseList;
    }

    public Business getBusinessById(Long id) {
        return businessRepository.findById(id)
            .orElseThrow(BusinessException::businessNotFound);
    }
}

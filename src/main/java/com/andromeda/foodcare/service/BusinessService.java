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
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
public class BusinessService {

    private final AuthService authService;
    private final RatingService ratingService;
    private final BusinessRepository businessRepository;
    private final BusinessMapper businessMapper;
    private final DistanceService distanceService;

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

    public List<BusinessResponse> getTopRated(Integer quantity) {
        log.info("Getting top rated businesses");
        Map<Long, Float> businessesRatings = ratingService.getAvgBusinessesRatings();

        if (quantity != null && businessesRatings.size() > 0) {
            businessesRatings = businessesRatings.entrySet().stream()
                    .limit(getQuantityOrMax(businessesRatings.size(), quantity))
                    .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
        }

        return businessesRatings.keySet().stream()
                .map(this::getBusinessById)
                .map(businessMapper::toBusinessResponse)
                .collect(Collectors.toList());
    }

    private Integer getQuantityOrMax(int size, Integer quantity) {
        return size < quantity ? size : quantity;
    }

    public List<BusinessResponse> searchForBusiness(String name, Double latitude, Double longitude) {
        List<Business> businessList = businessRepository
                .getAllByNameIgnoreCaseContainingAndAddress_CityIgnoreCase(name, distanceService.getCityFromCoordinates(longitude, latitude));

        return businessList.stream().map(businessMapper::toBusinessResponse).collect(Collectors.toList());
    }
}

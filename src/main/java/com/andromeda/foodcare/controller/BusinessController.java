package com.andromeda.foodcare.controller;

import com.andromeda.controller.BusinessApi;
import com.andromeda.dto.BusinessPayload;
import com.andromeda.dto.BusinessResponse;
import com.andromeda.foodcare.service.BusinessService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class BusinessController implements BusinessApi {

    private final BusinessService businessService;

    @Override
    public ResponseEntity<BusinessResponse> addBusiness(BusinessPayload businessPayload) {
        return ResponseEntity.ok(businessService.addBusiness(businessPayload));
    }

    @Override
    public ResponseEntity<BusinessResponse> getCurrentUserBusiness() {
        return ResponseEntity.ok(businessService.getCurrentUserBusiness());
    }

    @Override
    public ResponseEntity<List<BusinessResponse>> getBusinessesList(String city) {
        return ResponseEntity.ok(businessService.getAllBusinessesList(city));
    }

    @Override
    public ResponseEntity<List<BusinessResponse>> findBusinesses(String name) {
        return ResponseEntity.ok(businessService.findBusinessByName(name));
    }

    @Override
    public ResponseEntity<List<BusinessResponse>> getTopRated(Integer quantity) {
        return ResponseEntity.ok(businessService.getTopRated(quantity));
    }
}

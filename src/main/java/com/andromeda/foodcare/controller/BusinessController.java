package com.andromeda.foodcare.controller;

import com.andromeda.controller.BusinessApi;
import com.andromeda.dto.BusinessPayload;
import com.andromeda.dto.BusinessResponse;
import com.andromeda.foodcare.service.BusinessService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
public class BusinessController implements BusinessApi {

    private final BusinessService businessService;

    @Override
    public ResponseEntity<BusinessPayload> addBusiness(BusinessPayload businessPayload) {
        return ResponseEntity.ok(businessService.addBusiness(businessPayload));
    }

    @Override
    public ResponseEntity<BusinessPayload> getCurrentUserBusiness() {
        return ResponseEntity.ok(businessService.getCurrentUserBusiness());
    }

    @Override
    public ResponseEntity<List<BusinessResponse>> getBusinessesList(String city) {
        return ResponseEntity.ok(businessService.getAllBusinessesList(city));
    }
}

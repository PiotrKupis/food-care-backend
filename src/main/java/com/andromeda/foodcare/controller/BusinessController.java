package com.andromeda.foodcare.controller;

import com.andromeda.controller.BusinessApi;
import com.andromeda.dto.BusinessPayload;
import com.andromeda.foodcare.service.BusinessService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class BusinessController implements BusinessApi {

    private final BusinessService businessService;

    @Override
    public ResponseEntity<BusinessPayload> addBusiness(BusinessPayload businessPayload) {
        return ResponseEntity.ok(businessService.addBusiness(businessPayload));
    }

}

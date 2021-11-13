package com.andromeda.foodcare.controller;

import com.andromeda.controller.ProductApi;
import com.andromeda.dto.ProductPayload;
import com.andromeda.dto.ProductResponse;
import com.andromeda.foodcare.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ProductController implements ProductApi {

    private final ProductService productService;

    @Override
    public ResponseEntity<ProductPayload> addProduct(ProductPayload productPayload) {
        return ResponseEntity.ok(productService.addProduct(productPayload));
    }

    @Override
    public ResponseEntity<ProductResponse>getProduct(Long id) {
        return ResponseEntity.ok(productService.getProduct(id));
    }
}

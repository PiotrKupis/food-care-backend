package com.andromeda.foodcare.controller;

import com.andromeda.controller.ProductApi;
import com.andromeda.dto.ProductPayload;
import com.andromeda.dto.ProductResponse;
import com.andromeda.foodcare.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class ProductController implements ProductApi {

    private final ProductService productService;

    @Override
    public ResponseEntity<ProductResponse> addProduct(ProductPayload productPayload) {
        return ResponseEntity.ok(productService.addProduct(productPayload));
    }

    @Override
    public ResponseEntity<ProductResponse> getProduct(Long id) {
        return ResponseEntity.ok(productService.getProduct(id));
    }

    @Override
    public ResponseEntity<List<ProductResponse>> getProductsList(Long ownerId) {
        return ResponseEntity.ok(productService.getProductsList(ownerId));
    }

    @Override
    public ResponseEntity<Void> deleteProduct(Long id) {
        return productService.deleteProduct(id);
    }
}

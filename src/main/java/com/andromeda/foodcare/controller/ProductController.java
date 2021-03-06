package com.andromeda.foodcare.controller;

import com.andromeda.controller.ProductApi;
import com.andromeda.dto.ProductPayload;
import com.andromeda.dto.ProductResponse;
import com.andromeda.foodcare.service.ProductService;

import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<String> deleteProduct(Long id) {
        return ResponseEntity.ok(productService.deleteProduct(id));
    }

    @Override
    public ResponseEntity<List<ProductResponse>> getLatestProducts(Integer quantity) {
        return ResponseEntity.ok(productService.getLatestProducts(quantity));
    }

    @Override
    public ResponseEntity<List<ProductResponse>> searchProduct(String name, Double lon, Double lat) {
        return ResponseEntity.ok(productService.searchForProduct(name, lon, lat));
    }

    @Override
    public ResponseEntity<List<ProductResponse>> searchProductByCity(String name, String city) {
        return ResponseEntity.ok(productService.searchForProductByCity(name, city));
    }

    @Override
    public ResponseEntity<List<ProductResponse>> searchProductByName(String name) {
        return ResponseEntity.ok(productService.searchForProductByName(name));
    }

    @Override
    public ResponseEntity<List<ProductResponse>> getProductsFromOderByBusinessId(Integer businessId) {
        return ResponseEntity.ok(productService.getProductsFromOderByBusinessId(businessId));
    }

    @Override
    public ResponseEntity<List<ProductResponse>> getProductsFromOderByUserId(Integer userId) {
        return ResponseEntity.ok(productService.getProductsFromOderByUserId(userId));
    }
}

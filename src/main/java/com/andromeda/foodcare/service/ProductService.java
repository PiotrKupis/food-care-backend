package com.andromeda.foodcare.service;

import com.andromeda.dto.ProductPayload;
import com.andromeda.foodcare.mapper.ProductMapper;
import com.andromeda.foodcare.model.Product;
import com.andromeda.foodcare.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@AllArgsConstructor
@Service
public class ProductService {

    private final AuthService authService;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Transactional
    public ProductPayload addProduct(ProductPayload productPayload) {
        Product product = productMapper.toProduct(productPayload);
        log.info("Adding a new product " + product.getName());

        product.setOwnerId(authService.getCurrentUser().getId());
        product = productRepository.save(product);
        return productMapper.toProductPayload(product);
    }
}

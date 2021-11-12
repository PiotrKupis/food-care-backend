package com.andromeda.foodcare.service;

import com.andromeda.dto.ProductPayload;
import com.andromeda.foodcare.mapper.ProductMapper;
import com.andromeda.foodcare.model.Product;
import com.andromeda.foodcare.repository.ProductRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Map;

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


        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "food-care",
                "api_key", "961217742925776",
                "api_secret", "0FXcNuucLmOTwlv3Qw-Tco7uEBc",
                "secure", true));
        try {
            Map uploadResult = cloudinary.uploader().upload(productPayload.getImage(), ObjectUtils.emptyMap());
            product.setLinkToResource((String) uploadResult.get("url"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        product = productRepository.save(product);
        return productMapper.toProductPayload(product);
    }

    public ProductPayload getProduct(Long id) {

        return productMapper.toProductPayload(productRepository.getById(id));
    }
}

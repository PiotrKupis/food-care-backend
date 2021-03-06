package com.andromeda.foodcare.service;

import com.andromeda.dto.NearestBusinessResponse;
import com.andromeda.dto.ProductPayload;
import com.andromeda.dto.ProductResponse;
import com.andromeda.foodcare.mapper.ProductMapper;
import com.andromeda.foodcare.model.Business;
import com.andromeda.foodcare.model.Order;
import com.andromeda.foodcare.model.Product;
import com.andromeda.foodcare.repository.BusinessRepository;
import com.andromeda.foodcare.repository.OrderRepository;
import com.andromeda.foodcare.repository.ProductRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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
    private final DistanceService distanceService;
    private final BusinessRepository businessRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public ProductResponse addProduct(ProductPayload productPayload) {
        Product product = productMapper.toProduct(productPayload);
        product.setCreationDate(LocalDateTime.now());
        log.info("Adding a new product " + product.getName());

        product.setOwnerId(authService.getCurrentUser().getBusiness().getId());

        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "food-care",
            "api_key", "961217742925776",
            "api_secret", "0FXcNuucLmOTwlv3Qw-Tco7uEBc",
            "secure", true));
        try {
            Map uploadResult = cloudinary.uploader()
                .upload(productPayload.getImage(), ObjectUtils.emptyMap());
            product.setLinkToResource((String) uploadResult.get("url"));
            System.out.println(uploadResult);
            product.setPublicId((String) uploadResult.get("public_id"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        product = productRepository.save(product);
        return productMapper.toProductResponse(product);
    }

    public ProductResponse getProduct(Long id) {
        return productMapper.toProductResponse(productRepository.getById(id));
    }

    // getting products by business id
    public List<ProductResponse> getProductsList(Long businessId) {
        List<Product> productsList = productRepository.getAllByOwnerId(businessId);

        return productsList.stream()
            .filter(product -> !isProductSold(product))
            .map(productMapper::toProductResponse)
            .collect(Collectors.toList());
    }

    public String deleteProduct(Long id) {

        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "food-care",
            "api_key", "961217742925776",
            "api_secret", "0FXcNuucLmOTwlv3Qw-Tco7uEBc",
            "secure", true));

        Product product = productRepository.getById(id);
        try {
            cloudinary.uploader().destroy(product.getPublicId(), ObjectUtils.emptyMap());
        } catch (IOException e) {
            e.printStackTrace();
        }

        productRepository.delete(product);
        return "\"Product deleted successfully.\"";
    }

    public List<ProductResponse> getLatestProducts(Integer quantity) {
        log.info("Getting the latest products");
        List<Product> products = productRepository.findAll().stream()
            .sorted(Comparator.comparing(Product::getCreationDate).reversed())
            .collect(Collectors.toList());

        if (quantity != null) {
            products = products.subList(0, getQuantityOrMax(products, quantity));
        }

        products = products.stream()
            .filter(product -> !isProductSold(product))
            .collect(Collectors.toList());

        return products.stream()
            .map(productMapper::toProductResponse)
            .collect(Collectors.toList());
    }

    private Integer getQuantityOrMax(List<Product> products, Integer quantity) {
        return products.size() < quantity ? products.size() : quantity;
    }

    public List<ProductResponse> searchForProduct(String name, Double lat, Double lon) {
        List<NearestBusinessResponse> businessesInCity = distanceService.getNearestBusinessesFromCoordinates(
            lat, lon);
        List<Product> products = new ArrayList<>();
        for (NearestBusinessResponse business : businessesInCity) {
            products.addAll(productRepository.getAllByNameIgnoreCaseContainingAndOwnerId(name,
                business.getId()));
        }

        products = products.stream()
            .filter(product -> !isProductSold(product))
            .collect(Collectors.toList());

        return products.stream().map(productMapper::toProductResponse).collect(Collectors.toList());
    }

    public List<ProductResponse> searchForProductByCity(String name, String city) {

        List<Business> businessesInCity = businessRepository.getAllByAddress_City(city);
        List<Product> products = new ArrayList<>();
        for (Business business : businessesInCity) {
            products.addAll(productRepository.getAllByNameIgnoreCaseContainingAndOwnerId(name,
                business.getId()));
        }

        products = products.stream()
            .filter(product -> !isProductSold(product))
            .collect(Collectors.toList());

        return products.stream().map(productMapper::toProductResponse).collect(Collectors.toList());
    }

    public List<ProductResponse> searchForProductByName(String name) {

        List<Product> products = productRepository.getAllByNameIgnoreCaseContaining(name);

        products = products.stream()
            .filter(product -> !isProductSold(product))
            .collect(Collectors.toList());

        return products.stream()
            .map(productMapper::toProductResponse)
            .collect(Collectors.toList());
    }

    private boolean isProductSold(Product product) {
        return !orderRepository.getAllByProductId(product.getId()).isEmpty();
    }

    public List<ProductResponse> getProductsFromOderByBusinessId(Integer businessId) {
        List<Order> orders = orderRepository.getAllByBusinessId(Long.valueOf(businessId));

        List<Product> products = orders.stream()
            .map(order -> productRepository.getById(order.getProductId()))
            .collect(Collectors.toList());

        return products.stream()
            .map(productMapper::toProductResponse)
            .collect(Collectors.toList());
    }

    public List<ProductResponse> getProductsFromOderByUserId(Integer userId) {
        List<Order> orders = orderRepository.getAllByUserId(Long.valueOf(userId));

        List<Product> products = orders.stream()
            .map(order -> productRepository.getById(order.getProductId()))
            .collect(Collectors.toList());

        return products.stream()
            .map(productMapper::toProductResponse)
            .collect(Collectors.toList());
    }
}

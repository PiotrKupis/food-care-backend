package com.andromeda.foodcare.mapper;

import com.andromeda.dto.ProductPayload;
import com.andromeda.dto.ProductResponse;
import com.andromeda.foodcare.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class ProductMapper {

    @Mapping(target = "id", ignore = true)
    public abstract Product toProduct(ProductPayload productPayload);

    public abstract ProductPayload toProductPayload(Product product);

    public abstract ProductResponse toProductResponse(Product product);
}

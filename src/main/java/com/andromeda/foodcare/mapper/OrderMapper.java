package com.andromeda.foodcare.mapper;

import com.andromeda.dto.OrderPayload;
import com.andromeda.dto.OrderResponse;
import com.andromeda.foodcare.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class OrderMapper {

    @Mapping(target = "id", ignore = true)
    public abstract Order toOrder(OrderPayload orderPayload);

    public abstract OrderPayload toOrderPayload(Order order);

    public abstract OrderResponse toOrderResponse(Order order);
}

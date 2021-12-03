package com.andromeda.foodcare.mapper;

import com.andromeda.dto.BusinessPayload;
import com.andromeda.dto.BusinessResponse;
import com.andromeda.dto.NearestBusinessResponse;
import com.andromeda.foodcare.model.Business;

import java.time.LocalTime;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class BusinessMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "address.country", source = "country")
    @Mapping(target = "address.city", source = "city")
    @Mapping(target = "address.zipCode", source = "zipCode")
    @Mapping(target = "address.street", source = "street")
    @Mapping(target = "address.streetNumber", source = "streetNumber")
    @Mapping(target = "businessType", expression = "java(com.andromeda.foodcare.enums.BusinessType.valueOf(businessPayload.getBusinessType().toString()))")
    @Mapping(target = "openHour", expression = "java(java.time.LocalTime.parse(businessPayload.getOpenHour()))")
    @Mapping(target = "closeHour", expression = "java(java.time.LocalTime.parse(businessPayload.getCloseHour()))")
    public abstract Business toBusiness(BusinessPayload businessPayload);

    @Mapping(target = "country", source = "address.country")
    @Mapping(target = "city", source = "address.city")
    @Mapping(target = "zipCode", source = "address.zipCode")
    @Mapping(target = "street", source = "address.street")
    @Mapping(target = "streetNumber", source = "address.streetNumber")
    @Mapping(target = "businessType", expression = "java(com.andromeda.dto.BusinessPayload.BusinessTypeEnum.valueOf(business.getBusinessType().toString()))")
    @Mapping(target = "openHour", expression = "java(timeToString(business.getOpenHour()))")
    @Mapping(target = "closeHour", expression = "java(timeToString(business.getCloseHour()))")
    public abstract BusinessPayload toBusinessPayload(Business business);

    @Mapping(target = "country", source = "address.country")
    @Mapping(target = "city", source = "address.city")
    @Mapping(target = "zipCode", source = "address.zipCode")
    @Mapping(target = "street", source = "address.street")
    @Mapping(target = "streetNumber", source = "address.streetNumber")
    public abstract BusinessResponse toBusinessResponse(Business business);

    @Mapping(target = "country", source = "address.country")
    @Mapping(target = "city", source = "address.city")
    @Mapping(target = "zipCode", source = "address.zipCode")
    @Mapping(target = "street", source = "address.street")
    @Mapping(target = "streetNumber", source = "address.streetNumber")
    public abstract NearestBusinessResponse toNearestBusinessResponse(Business business);

    public String timeToString(LocalTime time) {
        return time.format(java.time.format.DateTimeFormatter.ofPattern("HH:mm"));
    }
}

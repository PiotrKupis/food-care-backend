package com.andromeda.foodcare.mapper;

import com.andromeda.dto.DetailedUserPayload;
import com.andromeda.dto.UpdateUserResponse;
import com.andromeda.foodcare.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "password", ignore = true)
    DetailedUserPayload toDetailedUserPayload(User user);

    UpdateUserResponse toUpdateUserResponse(User user);
}

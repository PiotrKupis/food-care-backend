package com.andromeda.foodcare.mapper;

import com.andromeda.dto.RatingPayload;
import com.andromeda.foodcare.model.Rating;
import com.andromeda.foodcare.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RatingMapper {

    RatingPayload toRatingPayload(Rating rating);

    @Mapping(target = "userId", source = "user.id")
    Rating toRating(RatingPayload ratingPayload, User user);
}

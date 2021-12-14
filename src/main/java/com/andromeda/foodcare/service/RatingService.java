package com.andromeda.foodcare.service;

import com.andromeda.dto.RatingPayload;
import com.andromeda.foodcare.exceptions.BusinessException;
import com.andromeda.foodcare.mapper.RatingMapper;
import com.andromeda.foodcare.model.Rating;
import com.andromeda.foodcare.model.User;
import com.andromeda.foodcare.repository.BusinessRepository;
import com.andromeda.foodcare.repository.RatingRepository;
import com.andromeda.foodcare.utils.TotalRate;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@AllArgsConstructor
@Service
public class RatingService {

    private final AuthService authService;
    private final RatingRepository ratingRepository;
    private final BusinessRepository businessRepository;
    private final RatingMapper ratingMapper;

    @Transactional
    public RatingPayload addBusinessRating(RatingPayload ratingPayload) {
        log.info("Adding a new rating");
        businessRepository.findById(ratingPayload.getBusinessId())
            .orElseThrow(BusinessException::businessNotFound);

        User currentUser = authService.getCurrentUser();
        Rating rating = ratingMapper.toRating(ratingPayload, currentUser);

        ratingRepository.findRatingByBusinessIdAndUserId(ratingPayload.getBusinessId(),
            currentUser.getId()).ifPresentOrElse(
            r -> r.setRating(ratingPayload.getRating()),
            () -> ratingRepository.save(rating)
        );

        return ratingMapper.toRatingPayload(rating);
    }

    public List<TotalRate> getAvgBusinessesRatings() {
        log.info("Calculating top rated businesses");
        List<TotalRate> totalRates = new ArrayList<>();

        Set<Long> businessIds = ratingRepository.findAll().stream()
            .map(Rating::getBusinessId)
            .collect(Collectors.toSet());
        businessIds.forEach(id -> totalRates.add(new TotalRate(id, calculateRating(id))));
        totalRates.sort(Collections.reverseOrder());

        return totalRates;
    }

    public RatingPayload getBusinessRating(Long id) {
        log.info("Calculating rating of the business");
        businessRepository.findById(id)
            .orElseThrow(BusinessException::businessNotFound);
        RatingPayload ratingPayload = new RatingPayload();
        ratingPayload.setBusinessId(id);
        ratingPayload.setRating(calculateRating(id));
        return ratingPayload;
    }

    private Float calculateRating(Long id) {
        int quantity = ratingRepository.findRatingByBusinessId(id).size();
        Float totalRate = ratingRepository.findRatingByBusinessId(id).stream()
            .map(Rating::getRating)
            .reduce(0F, Float::sum);
        if (quantity == 0) {
            return 0F;
        }

        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        Double rate = (double)totalRate / quantity;
        String format = decimalFormat.format(rate).replace(",",".");
        return Float.valueOf(format);
    }
}

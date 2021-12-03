package com.andromeda.foodcare.model;

import com.andromeda.dto.NearestBusinessResponse;

import java.util.Comparator;

public class BusinessComparator implements Comparator<NearestBusinessResponse> {

    @Override
    public int compare(NearestBusinessResponse o1, NearestBusinessResponse o2) {
        return Integer.compare(o1.getDistance(), o2.getDistance());
    }
}

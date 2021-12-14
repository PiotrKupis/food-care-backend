package com.andromeda.foodcare.utils;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@NotNull
@Data
public class TotalRate implements Comparable<TotalRate> {

    private Long businessId;
    private Float totalRate;

    @Override
    public int compareTo(@org.jetbrains.annotations.NotNull TotalRate o) {
        return this.totalRate.compareTo(o.getTotalRate());
    }
}

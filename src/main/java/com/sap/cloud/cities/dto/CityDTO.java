package com.sap.cloud.cities.dto;

import lombok.*;
import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CityDTO {
    private Long id;
    private String name;
    private Double area;
    private Integer population;
    private Double density;

    public void setDensity(Double area, Integer population){
        Double convertedPopulation = NumberUtils.toDouble(String.valueOf(population));
        this.density = BigDecimal.valueOf(convertedPopulation / area).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}

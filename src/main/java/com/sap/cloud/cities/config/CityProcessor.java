package com.sap.cloud.cities.config;

import com.sap.cloud.cities.entities.City;
import org.jetbrains.annotations.NotNull;
import org.springframework.batch.item.ItemProcessor;

import java.util.HashSet;
import java.util.Set;

public class CityProcessor implements ItemProcessor<City, City> {

    private final Set<String> seenCities = new HashSet<>();

    @Override
    public City process(@NotNull City city){
        if (!seenCities.contains(city.getName())) {
            System.out.println("New city: " + city.getName());
            seenCities.add(city.getName());
            return city;
        } else {
            System.out.println("Duplicate city: " + city.getName());
            return null;
        }
    }
}

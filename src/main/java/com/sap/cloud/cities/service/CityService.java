package com.sap.cloud.cities.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.sap.cloud.cities.entities.City;
import com.sap.cloud.cities.enums.SortField;
import com.sap.cloud.cities.predicate.CityPredicateBuilder;
import com.sap.cloud.cities.repository.CityRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.StreamSupport;

@Service
public class CityService {
    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public List<City> getAllCities(SortField sortField, Sort.Direction sortDirection, String filterByNameChunk) {
        Sort sort = Sort.by(sortDirection, sortField.getDatabaseField());
        if (filterByNameChunk != null && !filterByNameChunk.isBlank()) {
            Iterable<City> citiesData = this.cityRepository.findAll(this.generatePredicate(filterByNameChunk), sort);
            return StreamSupport.stream(citiesData.spliterator(), false).toList();
        } else {
            return this.cityRepository.findAll(sort);
        }
    }

    public City getCityById(Long id) {
        return (City) this.cityRepository.findById(id).orElse(null);
    }

    public City saveCity(City city) {
        city.setDensity((double) city.getPopulation() / city.getArea());
        return this.cityRepository.save(city);
    }

    private BooleanExpression generatePredicate(String filter) {
        CityPredicateBuilder cityPredicateBuilder = new CityPredicateBuilder();
        if (filter != null) {
            Pattern pattern = Pattern.compile("(\\w+?)([:<>\\-])(\\w+?),");
            Matcher matcher = pattern.matcher(filter + ",");

            while (matcher.find()) {
                cityPredicateBuilder.with(matcher.group(1), matcher.group(2), matcher.group(3));
            }
        }

        return cityPredicateBuilder.build();
    }
}

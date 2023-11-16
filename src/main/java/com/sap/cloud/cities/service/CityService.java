package com.sap.cloud.cities.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.sap.cloud.cities.entities.City;
import com.sap.cloud.cities.enums.FilterCriteria;
import com.sap.cloud.cities.enums.SortField;
import com.sap.cloud.cities.predicate.CityPredicateBuilder;
import com.sap.cloud.cities.repository.CityRepository;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

    public List<City> getAllCities(SortField sortField, Sort.Direction sortDirection, String filterQuery) {
        Sort sort = Sort.by(sortDirection, sortField.getDatabaseField().toLowerCase());
        if (filterQuery != null && !filterQuery.isBlank()) {
            Iterable<City> citiesData = this.cityRepository.findAll(this.generatePredicate(filterQuery), sort);
            return StreamSupport.stream(citiesData.spliterator(), false).toList();
        } else {
            return this.cityRepository.findAll(sort);
        }
    }

    public City getCityById(Long id) {
        return this.cityRepository.findById(id).orElse(null);
    }

    public City saveCity(City city) {
        Double areaConverted= NumberUtils.createDouble(String.valueOf(city.getArea()));
        Double populationConverted= NumberUtils.createDouble(String.valueOf(city.getPopulation()));
        BigDecimal density = BigDecimal.valueOf(populationConverted/areaConverted).setScale(2, RoundingMode.HALF_UP);
        city.setDensity(density.doubleValue());
        return this.cityRepository.save(city);
    }

    private BooleanExpression generatePredicate(String filter) {
        CityPredicateBuilder cityPredicateBuilder = new CityPredicateBuilder();
        if (filter != null) {
            Pattern pattern = Pattern.compile("(\\w+)([><=:\\-])(\\w+\\|?\\w+)");
            Matcher matcher = pattern.matcher(filter + ",");

            while (matcher.find()) {
                cityPredicateBuilder.with(matcher.group(1), FilterCriteria.fromString(matcher.group(2)), matcher.group(3));
            }
        }

        return cityPredicateBuilder.build();
    }
}

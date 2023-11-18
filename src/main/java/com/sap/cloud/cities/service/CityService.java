package com.sap.cloud.cities.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.sap.cloud.cities.dto.CityDTO;
import com.sap.cloud.cities.entities.City;
import com.sap.cloud.cities.enums.FilterCriteria;
import com.sap.cloud.cities.enums.SortField;
import com.sap.cloud.cities.predicate.CityPredicateBuilder;
import com.sap.cloud.cities.repository.CityRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.StreamSupport;

@Service
public class CityService {
    private final CityRepository cityRepository;

    private final ModelMapper modelMapper;

    public CityService(CityRepository cityRepository, ModelMapper modelMapper) {
        this.cityRepository = cityRepository;
        this.modelMapper = modelMapper;
    }

    public List<CityDTO> getAllCities(SortField sortField, Sort.Direction sortDirection, String filterQuery) {
        Sort sort = Sort.by(sortDirection, sortField.getDatabaseField().toLowerCase());
        if (filterQuery != null && !filterQuery.isBlank()) {
            Iterable<City> citiesData = this.cityRepository.findAll(this.generatePredicate(filterQuery), sort);
            return convertToDto(StreamSupport.stream(citiesData.spliterator(), false).toList());
        } else {
            return convertToDto(this.cityRepository.findAll(sort));
        }
    }

    public City getCityById(Long id) {
        return this.cityRepository.findById(id).orElse(null);
    }

    public CityDTO saveCity(CityDTO city) {
        City newCity = this.modelMapper.map(city, City.class);
        return modelMapper.map(this.cityRepository.save(newCity), CityDTO.class);
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

    private List<CityDTO> convertToDto(List<City> cities) {
        return cities.stream().map(city -> {
            CityDTO cityDTO = modelMapper.map(city, CityDTO.class);
            cityDTO.setDensity(city.getArea(), city.getPopulation());
            return cityDTO;
        }).toList();
    }
}

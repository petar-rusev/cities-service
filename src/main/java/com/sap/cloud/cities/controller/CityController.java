package com.sap.cloud.cities.controller;

import com.sap.cloud.cities.entities.City;
import com.sap.cloud.cities.enums.SortField;
import com.sap.cloud.cities.service.CityService;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping({"/api/v1/cities"})
public class CityController {
    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping
    public List<City> getAllCities(
            @RequestParam(name = "sortBy", defaultValue = "name", required = false) String sortField,
            @RequestParam(name = "order", defaultValue = "ASC", required = false) String sortDirection,
            @RequestParam(name = "filter", defaultValue = "", required = false) String filterQuery) {
        return this.cityService.getAllCities(SortField.fromString(sortField), Sort.Direction.fromString(sortDirection), filterQuery);
    }

    @GetMapping({"/{id}"})
    public City getCityById(@PathVariable Long id) {
        return this.cityService.getCityById(id);
    }

    @PostMapping
    public City saveCity(@RequestBody City city) {
        return this.cityService.saveCity(city);
    }
}

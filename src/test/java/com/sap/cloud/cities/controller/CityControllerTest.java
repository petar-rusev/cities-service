package com.sap.cloud.cities.controller;

import com.sap.cloud.cities.entities.City;
import com.sap.cloud.cities.enums.SortField;
import com.sap.cloud.cities.service.CityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CityControllerTest {

    @Mock
    private CityService cityService;

    @InjectMocks
    private CityController cityController;

    private City sampleCity1, sampleCity2;

    @BeforeEach
    void setUp() {
        sampleCity1 = new City();
        sampleCity1.setId(1L);
        sampleCity1.setName("Berlin");
        sampleCity1.setArea(891.85);
        sampleCity1.setPopulation(3769495);
        sampleCity1.setDensity(4226.89);

        sampleCity2 = new City();
        sampleCity2.setId(2L);
        sampleCity2.setName("Hamburg");
        sampleCity2.setArea(755.22);
        sampleCity2.setPopulation(1841179);
        sampleCity2.setDensity(2437.94);
    }

    @Test
    void getAllCities_shouldReturnListOfCities() {
        List<City> expectedCities = Arrays.asList(sampleCity1, sampleCity2);
        when(cityService.getAllCities(any(SortField.class), any(Sort.Direction.class), anyString())).thenReturn(expectedCities);

        List<City> actualCities = cityController.getAllCities("name", "ASC", "");

        assertAll(
                () -> assertNotNull(actualCities),
                () -> assertEquals(2, actualCities.size()),
                () -> assertEquals(expectedCities, actualCities)
        );
    }

    @Test
    void getAllCities_whenNoCities_shouldReturnEmptyList() {
        when(cityService.getAllCities(any(SortField.class), any(Sort.Direction.class), anyString())).thenReturn(Collections.emptyList());

        List<City> actualCities = cityController.getAllCities("name", "ASC", "");

        assertTrue(actualCities.isEmpty());
    }


    @Test
    void getCityById_shouldReturnCity() {
        when(cityService.getCityById(sampleCity1.getId())).thenReturn(sampleCity1);

        City actualCity = cityController.getCityById(sampleCity1.getId());

        assertAll(
                () -> assertNotNull(actualCity),
                () -> assertEquals(sampleCity1.getId(), actualCity.getId()),
                () -> assertEquals(sampleCity1.getName(), actualCity.getName())
        );
    }

    @Test
    void getCityById_whenCityNotFound_shouldReturnNull() {
        Long nonExistentCityId = 3L;
        when(cityService.getCityById(nonExistentCityId)).thenReturn(null);

        City actualCity = cityController.getCityById(nonExistentCityId);

        assertNull(actualCity);
    }

    @Test
    void saveCity_shouldSaveAndReturnCity() {
        when(cityService.saveCity(any(City.class))).thenReturn(sampleCity1);

        City actualCity = cityController.saveCity(sampleCity1);

        assertAll(
                () -> assertNotNull(actualCity),
                () -> assertEquals(sampleCity1.getId(), actualCity.getId()),
                () -> assertEquals(sampleCity1.getName(), actualCity.getName())
        );
    }

}
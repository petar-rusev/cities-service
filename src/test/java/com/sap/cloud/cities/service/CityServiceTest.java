package com.sap.cloud.cities.service;

import com.sap.cloud.cities.entities.City;
import com.sap.cloud.cities.enums.SortField;
import com.sap.cloud.cities.repository.CityRepository;
import com.sap.cloud.cities.service.CityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CityServiceTest {

    @Mock
    private CityRepository cityRepository;

    @InjectMocks
    private CityService cityService;

    private City sampleCity1, sampleCity2;

    @BeforeEach
    void setUp() {
        sampleCity1 = new City();
        sampleCity1.setId(1L);
        sampleCity1.setName("Berlin");
        sampleCity1.setArea(891.85);
        sampleCity1.setPopulation(3769495);
        sampleCity1.setDensity(4226.8912);

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
        when(cityRepository.findAll(any(Sort.class))).thenReturn(expectedCities);

        List<City> actualCities = cityService.getAllCities(SortField.NAME, Sort.Direction.ASC, "");

        assertAll(
                () -> assertNotNull(actualCities),
                () -> assertEquals(2, actualCities.size()),
                () -> assertEquals(expectedCities, actualCities)
        );
    }

    @Test
    void getCityById_shouldReturnCity() {
        when(cityRepository.findById(sampleCity1.getId())).thenReturn(Optional.of(sampleCity1));

        City actualCity = cityService.getCityById(sampleCity1.getId());

        assertAll(
                () -> assertNotNull(actualCity),
                () -> assertEquals(sampleCity1.getId(), actualCity.getId()),
                () -> assertEquals(sampleCity1.getName(), actualCity.getName())
        );
    }

    @Test
    void getCityById_whenCityNotFound_shouldReturnNull() {
        Long nonExistentCityId = 3L;
        when(cityRepository.findById(nonExistentCityId)).thenReturn(Optional.empty());

        City actualCity = cityService.getCityById(nonExistentCityId);

        assertNull(actualCity);
    }

    @Test
    void saveCity_shouldCalculateDensityAndSaveCity() {
        when(cityRepository.save(any(City.class))).thenAnswer(invocation -> {
            City city = invocation.getArgument(0);
            city.setId(3L); // Simulating the database assigning an ID
            return city;
        });

        City actualCity = cityService.saveCity(sampleCity1);

        assertAll(() -> assertNotNull(actualCity),
                () -> assertEquals(sampleCity1.getDensity(), actualCity.getDensity()));
    }

}

package com.sap.cloud.cities.service;

import com.sap.cloud.cities.dto.CityDTO;
import com.sap.cloud.cities.entities.City;
import com.sap.cloud.cities.enums.SortField;
import com.sap.cloud.cities.repository.CityRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class CityServiceTest {

    @MockBean
    private CityRepository cityRepository;

    @Autowired
    private ModelMapper modelMapper;

    private CityService cityService;

    @BeforeEach
    void setup() {
        cityService = new CityService(cityRepository, modelMapper);
    }

    @Nested
    @DisplayName("Test cases for getAllCities")
    class GetAllCitiesTests {

        @Test
        @DisplayName("Should return all cities")
        void shouldReturnAllCities() {
            City city = new City();
            city.setId(1L);
            city.setName("Berlin");
            city.setArea(891.85);
            city.setPopulation(3769495);
            when(cityRepository.findAll(any(Sort.class))).thenReturn(Collections.singletonList(city));

            List<CityDTO> result = cityService.getAllCities(SortField.NAME, Sort.Direction.ASC, "");

            assertAll(
                    () -> assertEquals(1, result.size(), "The size of the result list should be 1"),
                    () -> verify(cityRepository, times(1)).findAll(any(Sort.class)),
                    () -> assertEquals(city.getId(), result.get(0).getId()),
                    () -> assertEquals(city.getName(), result.get(0).getName()),
                    () -> assertEquals(city.getArea(), result.get(0).getArea()),
                    () -> assertEquals(city.getPopulation(), result.get(0).getPopulation()
            ));
        }

        @ParameterizedTest
        @ValueSource(strings = {"invalid", "123", "!@#"})
        @DisplayName("Should throw IllegalArgumentException for invalid parameters")
        void shouldThrowIllegalArgumentExceptionForInvalidParameters(String invalidParameter) {
            assertThrows(IllegalArgumentException.class, () -> cityService.getAllCities(SortField.fromString(invalidParameter), Sort.Direction.ASC, ""));
        }
    }

    @Nested
    @DisplayName("Test cases for getCityById")
    class GetCityByIdTests {

        @Test
        @DisplayName("Should return city by id")
        void shouldReturnCityById() {
            City city = new City();
            when(cityRepository.findById(anyLong())).thenReturn(Optional.of(city));

            City result = cityService.getCityById(1L);

            assertAll(
                    () -> assertEquals(city, result),
                    () -> verify(cityRepository, times(1)).findById(1L)
            );
        }

        @Test
        @DisplayName("Should throw ResponseStatusException for non-existent id")
        void shouldThrowResponseStatusExceptionForNonExistentId() {
            when(cityRepository.findById(anyLong())).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

            assertThrows(ResponseStatusException.class, () -> cityService.getCityById(999L));
        }
    }

    @Nested
    @DisplayName("Test cases for saveCity")
    class SaveCityTests {

        @Test
        @DisplayName("Should save city")
        void shouldSaveCity() {
            City city = new City();
            CityDTO cityDTO = new CityDTO();
            when(cityRepository.save(any())).thenReturn(city);

            CityDTO result = cityService.saveCity(new CityDTO());

            assertAll(
                    () -> assertEquals(cityDTO, result),
                    () -> verify(cityRepository, times(1)).save(any())
            );
        }

        @Test
        @DisplayName("Should throw ResponseStatusException for invalid data")
        void shouldThrowResponseStatusExceptionForInvalidData() {
            CityDTO invalidCity = new CityDTO();
            when(cityRepository.save(any())).thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST));

            assertThrows(ResponseStatusException.class, () -> cityService.saveCity(invalidCity));
        }
    }
}
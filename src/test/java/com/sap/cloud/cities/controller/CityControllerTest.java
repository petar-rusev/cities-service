package com.sap.cloud.cities.controller;

import com.sap.cloud.cities.dto.CityDTO;
import com.sap.cloud.cities.entities.City;
import com.sap.cloud.cities.enums.SortField;
import com.sap.cloud.cities.service.CityService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class CityControllerTest {

    @MockBean
    private CityService cityService;

    private CityController cityController;

    @BeforeEach
    void setup() {
        cityController = new CityController(cityService);
    }

    @Nested
    @DisplayName("Test cases for getAllCities")
    class GetAllCitiesTests {

        @Test
        @DisplayName("Should return all cities")
        void shouldReturnAllCities() {
            CityDTO cityDTO = new CityDTO();
            when(cityService.getAllCities(any(), any(), anyString())).thenReturn(Collections.singletonList(cityDTO));

            List<CityDTO> result = cityController.getAllCities("name", "ASC", "");

            assertAll(
                    () -> assertEquals(1, result.size()),
                    () -> verify(cityService, times(1)).getAllCities(SortField.NAME, Sort.Direction.ASC, "")
            );
        }

        @ParameterizedTest
        @ValueSource(strings = {"invalid", "123", "!@#"})
        @DisplayName("Should throw IllegalArgumentException for invalid parameters")
        void shouldThrowIllegalArgumentExceptionForInvalidParameters(String invalidParameter) {
            assertThrows(IllegalArgumentException.class, () -> cityController.getAllCities(invalidParameter, "ASC", ""));
        }
    }

    @Nested
    @DisplayName("Test cases for getCityById")
    class GetCityByIdTests {

        @Test
        @DisplayName("Should return city by id")
        void shouldReturnCityById() {
            City city = new City();
            when(cityService.getCityById(anyLong())).thenReturn(city);

            City result = cityController.getCityById(1L);

            assertAll(
                    () -> assertEquals(city, result),
                    () -> verify(cityService, times(1)).getCityById(1L)
            );
        }

        @Test
        @DisplayName("Should throw ResponseStatusException for non-existent id")
        void shouldThrowResponseStatusExceptionForNonExistentId() {
            when(cityService.getCityById(anyLong())).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

            assertThrows(ResponseStatusException.class, () -> cityController.getCityById(999L));
        }
    }

    @Nested
    @DisplayName("Test cases for saveCity")
    class SaveCityTests {

        @Test
        @DisplayName("Should save city")
        void shouldSaveCity() {
            CityDTO cityDTO = new CityDTO();
            when(cityService.saveCity(any())).thenReturn(cityDTO);

            CityDTO result = cityController.saveCity(new CityDTO());

            assertAll(
                    () -> assertEquals(cityDTO, result),
                    () -> verify(cityService, times(1)).saveCity(any())
            );
        }

        @Test
        @DisplayName("Should throw ResponseStatusException for invalid data")
        void shouldThrowResponseStatusExceptionForInvalidData() {
            CityDTO invalidCity = new CityDTO();
            when(cityService.saveCity(any())).thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST));

            assertThrows(ResponseStatusException.class, () -> cityController.saveCity(invalidCity));
        }
    }
}
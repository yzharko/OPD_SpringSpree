package ru.goth.service.impl;

import ru.goth.domain.dto.CityDto;
import ru.goth.repository.CityRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CityServiceImplTest {

    @Mock
    private CityRepository cityRepository;

    @InjectMocks
    private CityServiceImpl cityService;

    private final Long TEST_ID = 1L;
    private final String TEST_NAME = "Moscow";
    private final Long TEST_DELIVERY_TIME = 2L;

    @Test
    void createCityTest() {
        CityDto inputDto = new CityDto(TEST_NAME, TEST_DELIVERY_TIME);
        inputDto.setId(TEST_ID);

        CityDto repoResponse = new CityDto(TEST_NAME, TEST_DELIVERY_TIME);
        repoResponse.setId(TEST_ID);

        when(cityRepository.createCity(anyLong(), anyString(), anyLong())).thenReturn(repoResponse);

        CityDto result = cityService.createCity(inputDto);

        assertNotNull(result);
        assertEquals(TEST_ID, result.getId());
        assertEquals(TEST_NAME, result.getName());
        assertEquals(TEST_DELIVERY_TIME, result.getDeliveryTime());

        verify(cityRepository).createCity(TEST_ID, TEST_NAME, TEST_DELIVERY_TIME);
    }

    @Test
    void getCityByIdTest() {
        CityDto mockDto = new CityDto(TEST_NAME, TEST_DELIVERY_TIME);
        mockDto.setId(TEST_ID);

        when(cityRepository.getCityById(anyLong())).thenReturn(mockDto);

        CityDto result = cityService.getCityById(TEST_ID);

        assertNotNull(result);
        assertEquals(TEST_ID, result.getId());
        verify(cityRepository).getCityById(TEST_ID);
    }

    @Test
    void getAllCitiesTest() {
        CityDto mockDto = new CityDto(TEST_NAME, TEST_DELIVERY_TIME);
        mockDto.setId(TEST_ID);

        when(cityRepository.getAllCities()).thenReturn(Collections.singletonList(mockDto));

        List<CityDto> result = cityService.getAllCities();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(TEST_ID, result.get(0).getId());
        verify(cityRepository).getAllCities();
    }

    @Test
    void updateCityTest() {
        CityDto inputDto = new CityDto(TEST_NAME, TEST_DELIVERY_TIME);
        inputDto.setId(TEST_ID);

        CityDto repoResponse = new CityDto("UpdatedName", 3L);
        repoResponse.setId(TEST_ID);

        when(cityRepository.updateCity(anyLong(), anyString(), anyLong())).thenReturn(repoResponse);

        CityDto result = cityService.updateCity(TEST_ID, inputDto);

        assertNotNull(result);
        assertEquals(TEST_ID, result.getId());
        assertEquals("UpdatedName", result.getName());
        assertEquals(3L, result.getDeliveryTime());

        verify(cityRepository).updateCity(TEST_ID, TEST_NAME, TEST_DELIVERY_TIME);
    }

    @Test
    void deleteCityTest() {
        when(cityRepository.deleteCity(anyLong())).thenReturn(true);

        boolean result = cityService.deleteCity(TEST_ID);

        assertTrue(result);
        verify(cityRepository).deleteCity(TEST_ID);
    }

    @Test
    void existCityTest() {
        when(cityRepository.existCity(anyString())).thenReturn(TEST_ID);

        Long result = cityService.existCity(TEST_NAME);

        assertEquals(TEST_ID, result);
        verify(cityRepository).existCity(TEST_NAME);
    }
}

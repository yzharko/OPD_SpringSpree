package ru.goth.repository;

import ru.goth.domain.dto.CityDto;

import java.util.List;

public interface CityRepository {

    CityDto createCity(Long id, String name, Long deliveryTime);

    CityDto getCityById(Long id);

    List<CityDto> getAllCities();

    CityDto updateCity(Long id, String name, Long deliveryTime);

    boolean deleteCity(Long id);

    Long existCity(String name);
}

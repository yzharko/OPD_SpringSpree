package ru.goth.service;

import ru.goth.domain.entities.dto.CityDto;

import java.util.List;

public interface CityService {

    CityDto createCity(CityDto cityDto);

    CityDto readCityById(Long id);

    List<CityDto> readAllCities();

    CityDto updateCity(Long id, CityDto cityDto);

    boolean deleteCity(Long id);
}

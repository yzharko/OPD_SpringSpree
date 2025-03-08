package ru.goth.repository;

import ru.goth.domain.entities.dto.CityDto;

import java.util.List;

public interface CityRepository {

    CityDto createCity(CityDto cityDtoFromService);

    CityDto readCityById(Long id);

    List<CityDto> readAllCities();

    CityDto updateCity(Long id, CityDto cityDto);

    boolean deleteCity(Long id);
}


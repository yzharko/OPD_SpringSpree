package ru.goth.service;

import ru.goth.domain.dto.CityDto;

import java.util.List;

public interface CityService {

    CityDto createCity(CityDto cityDto);

    CityDto getCityById(Long id);

    List<CityDto> getAllCities();

    CityDto updateCity(Long id, CityDto cityDto);

    boolean deleteCity(Long id);
}

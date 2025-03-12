package ru.goth.repository;

import ru.goth.domain.entities.dto.CityDto;

import java.sql.Time;
import java.util.List;

public interface CityRepository {

    CityDto createCity(Long id, String name, Time deliveryTime);

    CityDto readCityById(Long id);

    List<CityDto> readAllCities();

    CityDto updateCity(Long id, String name, Time deliveryTime);

    boolean deleteCity(Long id);
}


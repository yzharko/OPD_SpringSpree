package ru.goth.repository;

import ru.goth.domain.dto.CityDto;

import java.sql.Time;
import java.util.List;

public interface CityRepository {

    CityDto createCity(Long id, String name, Time deliveryTime);

    CityDto getCityById(Long id);

    List<CityDto> getAllCities();

    CityDto updateCity(Long id, String name, Time deliveryTime);

    boolean deleteCity(Long id);
}

package ru.goth.repository;

import ru.goth.domain.entities.dto.CityDto;

import java.util.List;

public interface CityRepository {

    CityDto createCity(CityDto cityDtoFromService);

    List<CityDto> readAllCities();
}


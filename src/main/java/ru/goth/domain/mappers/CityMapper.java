package ru.goth.domain.mappers;

import org.mapstruct.Mapper;
import ru.goth.domain.dto.CityDto;
import ru.goth.domain.entities.City;

@Mapper
public interface CityMapper {

    CityDto toCityDto(City city);

    City toCity(CityDto cityDto);
}

package ru.goth.repository.mappers;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.goth.controller.DTOs.CityDto;
import ru.goth.repository.entities.City;

@Mapper(componentModel = "default")
public interface CityMapper {
    CityMapper INSTANCE = Mappers.getMapper(CityMapper.class);

    CityDto cityToCityDto(City city);
    City cityDtoToCity(CityDto cityDto);
}

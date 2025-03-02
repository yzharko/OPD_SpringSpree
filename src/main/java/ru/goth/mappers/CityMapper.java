package ru.goth.mappers;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.goth.dto.CityDto;
import ru.goth.entities.City;

@Mapper
public interface CityMapper {
    @Mapping(source = "cityName", target = "name")
    @Mapping(source = "Time", target = "deliveryTime")
    City cityDtoToCity(CityDto cityDto);

    CityDto cityToCityDto(City city);
}

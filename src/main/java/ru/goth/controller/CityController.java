package ru.goth.controller;

import ru.goth.controller.DTOs.CityDto;
import ru.goth.repository.CRUDs.CityCRUD;
import ru.goth.repository.entities.City;
import ru.goth.repository.mappers.CityMapper;
import ru.goth.repository.mappers.CityMapperImpl;

import java.sql.Time;

public class CityController {

    public static void main(String[] args)
    {
        CityMapper cityMapper = new CityMapperImpl();
        CityCRUD cityCRUD = new CityCRUD();
        CityDto cityDto = new CityDto("Sankt-Petersburg", Time.valueOf("0:0:52"));

        City city = cityMapper.cityDtoToCity(cityDto);
        cityCRUD.create(city);
    }
}

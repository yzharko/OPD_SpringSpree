package ru.goth.controller;

import ru.goth.domain.entities.dto.CityDto;
import ru.goth.repository.impl.CityRepositoryImpl;
import ru.goth.service.CityService;
import ru.goth.service.impl.CityServiceImpl;

import java.sql.Time;

public class Test {

    public static void main(String[] args) {
        CityService cityService = new CityServiceImpl(new CityRepositoryImpl());
        CityDto cityDto = new CityDto("Miami", Time.valueOf("06:06:06"));
        cityService.createCity(cityDto);
    }
}

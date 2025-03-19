package ru.goth.service.impl;

import ru.goth.domain.dto.CityDto;
import ru.goth.repository.impl.CityRepositoryImpl;
import ru.goth.service.CityService;

import java.util.List;

public class CityServiceImpl implements CityService {

    private final CityRepositoryImpl cityRepository;

    public CityServiceImpl(CityRepositoryImpl cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public CityDto createCity(CityDto cityDto) {
        CityDto newCityDto = new CityDto(cityRepository.createCity(
                cityDto.getId(),
                cityDto.getName(),
                cityDto.getDeliveryTime()));
        cityDto.setId(newCityDto.getId());
        cityDto.setName(newCityDto.getName());
        cityDto.setDeliveryTime(newCityDto.getDeliveryTime());
        return cityDto;
    }

    @Override
    public CityDto getCityById(Long id) {
        return cityRepository.getCityById(id);
    }

    @Override
    public List<CityDto> getAllCities() {
        return cityRepository.getAllCities();
    }

    @Override
    public CityDto updateCity(Long id, CityDto cityDto) {
        CityDto newCityDto = new CityDto(cityRepository.updateCity(
                id,
                cityDto.getName(),
                cityDto.getDeliveryTime()));
        cityDto.setId(newCityDto.getId());
        cityDto.setName(newCityDto.getName());
        cityDto.setDeliveryTime(newCityDto.getDeliveryTime());
        return cityDto;
    }

    @Override
    public boolean deleteCity(Long id) {
        return false;
    }
}

package ru.goth.domain.dto;

import ru.goth.deliveryTimeCalculator.DeliveryTimeCalculator;
import ru.goth.repository.impl.CityRepositoryImpl;
import ru.goth.service.CityService;
import ru.goth.service.impl.CityServiceImpl;

import java.sql.Connection;

public class CustomerDto {

    private Long id;
    private Long city_id;
    private String name;
    private String email;

    public CustomerDto() {}

    public CustomerDto(Long cityId, String name, String email) {
        this.city_id = cityId;
        this.name = name;
        this.email = email;
    }

    public CustomerDto(String cityName, String name, String email) {
        CityService serv = new CityServiceImpl(new CityRepositoryImpl());
        Long cityId = serv.existCity(cityName);

        if (cityId == null) {
            Long time = DeliveryTimeCalculator.getMinutes(cityName);
            CityDto dto = new CityDto(cityName, time);
            serv.createCity(dto);
        }

        cityId = serv.existCity(cityName);

        this.city_id = cityId;
        this.name = name;
        this.email = email;
    }

    public CustomerDto(Connection connection, String cityName, String name, String email) {
        CityService serv = new CityServiceImpl(new CityRepositoryImpl(connection));
        Long cityId = serv.existCity(cityName);

        if (cityId == null) {
            Long time = DeliveryTimeCalculator.getMinutes(cityName);
            CityDto dto = new CityDto(cityName, time);
            serv.createCity(dto);
        }

        cityId = serv.existCity(cityName);

        this.city_id = cityId;
        this.name = name;
        this.email = email;
    }

    public CustomerDto(CustomerDto CustomerDTO) {
        this.id = CustomerDTO.getId();
        this.name = CustomerDTO.getName();
        this.city_id = CustomerDTO.getCityId();
        this.email = CustomerDTO.getEmail();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCityId() {
        return city_id;
    }

    public void setCityId(Long id) {
        this.city_id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

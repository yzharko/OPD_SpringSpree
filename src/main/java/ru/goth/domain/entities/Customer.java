package ru.goth.domain.entities;

import ru.goth.deliveryTimeCalculator.DeliveryTimeCalculator;
import ru.goth.domain.dto.CityDto;
import ru.goth.repository.impl.CityRepositoryImpl;
import ru.goth.service.CityService;
import ru.goth.service.impl.CityServiceImpl;

import java.sql.Connection;
import java.util.Objects;

public class Customer {

    private Long id;
    private Long city_id;
    private String name;
    private String email;

    public Customer() {}

    public Customer(Long cityId, String name, String email) {
        this.city_id = cityId;
        this.name = name;
        this.email = email;
    }

    public Customer(String cityName, String name, String email) {
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

    public Customer(Connection connection, String cityName, String name, String email) {
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

    public Customer(Customer customer) {
        this.id = customer.getId();
        this.name = customer.getName();
        this.city_id = customer.getCityId();
        this.email = customer.getEmail();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass()
                != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id);
    }

    @Override
    public int hashCode() {
        if (name != null) {
            return 31 * 31 * city_id.hashCode() + 31 * city_id.hashCode() + email.hashCode();
        }
        return 0;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", city_id='" + city_id + '\'' +
                ", name='" + name + '\'' +
                ", email=" + email + '}';
    }
}

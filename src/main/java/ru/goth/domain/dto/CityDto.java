package ru.goth.domain.dto;

import java.sql.Time;

public class CityDto {

    private Long id;
    private String name;
    private Time deliveryTime;

    public CityDto() {
    }

    public CityDto(String name, Time deliveryTime) {
        this.name = name;
        this.deliveryTime = deliveryTime;
    }

    public CityDto(CityDto cityDto) {
        this.id = cityDto.getId();
        this.name = cityDto.getName();
        this.deliveryTime = cityDto.getDeliveryTime();
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

    public Time getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Time deliveryTime) {
        this.deliveryTime = deliveryTime;
    }
}

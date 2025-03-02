package ru.goth.dto;


import java.time.Duration;

public class CityDto {
    private Long id;
    private String name;
    private String deliveryTime;

    public CityDto() {
    }
    public CityDto(String name, String deliveryTime) {
        this.name = name;
        this.deliveryTime = deliveryTime;
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
    public String getTime() {
        return deliveryTime;
    }
    public void setTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }
}

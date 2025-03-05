package ru.goth.controller.DTOs;
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
    public Time getTime() {
        return deliveryTime;
    }
    public void setTime(Time deliveryTime) {
        this.deliveryTime = deliveryTime;
    }
}

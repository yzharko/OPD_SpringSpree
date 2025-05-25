package ru.goth.domain.dto;

import com.google.gson.annotations.SerializedName;

public class CityDto {

    private Long id;
    private String name;
    @SerializedName("delivery_time")
    private Long deliveryTime;

    public CityDto() {
    }

    public CityDto(String name, Long deliveryTime) {
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

    public Long getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Long deliveryTime) {
        this.deliveryTime = deliveryTime;
    }
}

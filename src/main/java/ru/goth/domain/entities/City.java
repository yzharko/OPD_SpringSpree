package ru.goth.domain.entities;

import java.sql.Time;
import java.util.Objects;

public class City {

    private Long id;
    private String name;
    private Time deliveryTime;

    public City() {
    }

    public City(String name, Time deliveryTime) {
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

    public Time getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Time deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        City city = (City) object;
        return Objects.equals(id, city.id) &&
                Objects.equals(name, city.name);
    }

    @Override
    public int hashCode() {
        if (name != null) {
            return 31 * name.hashCode() + deliveryTime.hashCode();
        }
        return 0;
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", deliveryTime=" + deliveryTime + '}';
    }
}

package ru.goth.entities;
import java.util.Objects;
import java.time.Duration;

public class City {

    private Long id;

    private String name;

    private Duration deliveryTime;

    public City() {
    }

    public City(String name, Duration deliveryTime) {
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

    public Duration getTime() {
        return deliveryTime;
    }

    public void setTime(Duration deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return Objects.equals(id, city.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", deliveryTime=" + deliveryTime +
                '}';
    }
}

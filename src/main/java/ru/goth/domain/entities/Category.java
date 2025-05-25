package ru.goth.domain.entities;

import java.util.Objects;

public class Category {

    private Long id;
    private String name;
    private String hazard;
    private String rarity;

    public Category() {
    }

    public Category(String name, String hazard, String rarity) {
        this.name = name;
        this.hazard = hazard;
        this.rarity = rarity;
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

    public String getHazard() {
        return hazard;
    }

    public void setHazard(String hazard) {
        this.hazard = hazard;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Category category = (Category) object;
        return Objects.equals(id, category.id) &&
                Objects.equals(name, category.name) &&
                Objects.equals(hazard, category.hazard) &&
                Objects.equals(rarity, category.rarity);
    }

    @Override
    public int hashCode() {
        if (name != null) {
            return 31^2 * name.hashCode() + 31 * hazard.hashCode() + rarity.hashCode();
        }
        return 0;
    }

    @Override
    public String toString() {
        return "Category{" + "id=" + id +
                ", name='" + name + '\'' +
                ", hazard=" + hazard +
                ", rarity=" + rarity + '}';
    }
}

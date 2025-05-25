package ru.goth.domain.entities;

import java.util.Objects;

public class Mythology {

    private Long id;
    private String name;

    public Mythology() {
    }

    public Mythology(String name) {
        this.name = name;
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

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Mythology mythology = (Mythology) object;
        return Objects.equals(id, mythology.id) &&
                Objects.equals(name, mythology.name);
    }

    @Override
    public int hashCode() {
        if (name != null) {
            return 31 * name.hashCode();
        }
        return 0;
    }

    @Override
    public String toString() {
        return "Mythology{" +
                "id=" + id +
                ", name='" + name + '\'' + '}';
    }
}

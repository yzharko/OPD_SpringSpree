package ru.goth.domain.entities;

import java.util.Objects;

public class Step {

    private Long id;
    private String name;
    private String description;

    public Step() {
    }

    public Step(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Step step = (Step) object;
        return Objects.equals(id, step.id) &&
                Objects.equals(name, step.name);
    }

    @Override
    public int hashCode() {
        if (name != null) {
            return 31 * name.hashCode() + (description != null ? description.hashCode() : 0);
        }
        return 0;
    }

    @Override
    public String toString() {
        return "Step{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}

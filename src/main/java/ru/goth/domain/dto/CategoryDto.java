package ru.goth.domain.dto;

public class CategoryDto {

    private Long id;
    private String name;
    private String hazard;
    private String rarity;

    public CategoryDto() {
    }

    public CategoryDto(String name, String hazard, String rarity) {
        this.name = name;
        this.hazard = hazard;
        this.rarity = rarity;
    }

    public CategoryDto(CategoryDto categoryDto) {
        this.id = categoryDto.getId();
        this.name = categoryDto.getName();
        this.hazard = categoryDto.getHazard();
        this.rarity = categoryDto.getRarity();
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
}

package ru.goth.domain.dto;

public class StepDto {

    private Long id;
    private String name;
    private String description;

    public StepDto() {
    }

    public StepDto(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public StepDto(StepDto stepDto) {
        this.id = stepDto.getId();
        this.name = stepDto.getName();
        this.description = stepDto.getDescription();
    }

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public String getDescription() {return description;}

    public void setDescription(String description) {this.description = description;}
}

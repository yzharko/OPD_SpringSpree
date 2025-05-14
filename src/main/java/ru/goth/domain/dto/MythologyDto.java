package ru.goth.domain.dto;

public class MythologyDto {

    private Long id;
    private String name;

    public MythologyDto() {
    }

    public MythologyDto(String name) {
        this.name = name;
    }

    public MythologyDto(MythologyDto mythologyDto) {
        this.id = mythologyDto.getId();
        this.name = mythologyDto.getName();
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

}

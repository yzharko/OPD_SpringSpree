package ru.goth.domain.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CategoryDtoTest {

    private static final Long TEST_ID = 1L;
    private static final String TEST_NAME = "Potion";
    private static final String TEST_HAZARD = "Low";
    private static final  String TEST_RARITY = "Common";

    @Test
    void getSetIdTest() {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(TEST_ID);
        assertEquals(TEST_ID, categoryDto.getId());
    }

    @Test
    void getNameTest() {
        CategoryDto categoryDto = new CategoryDto(TEST_NAME, TEST_HAZARD, TEST_RARITY);
        assertEquals(TEST_NAME, categoryDto.getName());
    }

    @Test
    void setNameTest() {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName(TEST_NAME);
        assertEquals(TEST_NAME, categoryDto.getName());
    }

    @Test
    void getHazardTest() {
        CategoryDto categoryDto = new CategoryDto(TEST_NAME, TEST_HAZARD, TEST_RARITY);
        assertEquals(TEST_HAZARD, categoryDto.getHazard());
    }

    @Test
    void setHazardTest() {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setHazard(TEST_HAZARD);
        assertEquals(TEST_HAZARD, categoryDto.getHazard());
    }

    @Test
    void getRarityTest() {
        CategoryDto categoryDto = new CategoryDto(TEST_NAME, TEST_HAZARD, TEST_RARITY);
        assertEquals(TEST_RARITY, categoryDto.getRarity());
    }

    @Test
    void setRarityTest() {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setRarity(TEST_RARITY);
        assertEquals(TEST_RARITY, categoryDto.getRarity());
    }

}

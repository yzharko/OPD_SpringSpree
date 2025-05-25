package ru.goth.domain.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

    private static final Long TEST_ID_F = 1L;
    private static final String TEST_NAME_F = "Potion";
    private static final String TEST_HAZARD_F = "Low";
    private static final  String TEST_RARITY_F = "Common";

    private static final Long TEST_ID_S = 2L;
    private static final String TEST_NAME_S = "Weapon";
    private static final String TEST_HAZARD_S = "High";
    private static final  String TEST_RARITY_S = "Rare";

    @Test
    void getSetIdTest() {
        Category category = new Category();
        category.setId(TEST_ID_F);
        assertEquals(TEST_ID_F, category.getId());
    }

    @Test
    void getNameTest() {
        Category category = new Category(TEST_NAME_F, TEST_HAZARD_F, TEST_RARITY_F);
        assertEquals(TEST_NAME_F, category.getName());
    }

    @Test
    void setNameTest() {
        Category category = new Category();
        category.setName(TEST_NAME_F);
        assertEquals(TEST_NAME_F, category.getName());
    }

    @Test
    void getHazardTest() {
        Category category = new Category(TEST_NAME_F, TEST_HAZARD_F, TEST_RARITY_F);
        assertEquals(TEST_HAZARD_F, category.getHazard());
    }

    @Test
    void setHazardTest() {
        Category category = new Category();
        category.setHazard(TEST_HAZARD_F);
        assertEquals(TEST_HAZARD_F, category.getHazard());
    }

    @Test
    void getRarityTest() {
        Category category = new Category(TEST_NAME_F, TEST_HAZARD_F, TEST_RARITY_F);
        assertEquals(TEST_RARITY_F, category.getRarity());
    }

    @Test
    void setRarityTest() {
        Category category = new Category();
        category.setRarity(TEST_RARITY_F);
        assertEquals(TEST_RARITY_F, category.getRarity());
    }

    @Test
    void equalsTest() {
        Category category1 = new Category(TEST_NAME_F, TEST_HAZARD_F, TEST_RARITY_F);
        category1.setId(TEST_ID_F);

        Category category2 = new Category(TEST_NAME_F, TEST_HAZARD_F, TEST_RARITY_F);
        category2.setId(TEST_ID_F);

        assertEquals(category1, category1);

        assertEquals(category1, category2);
        assertEquals(category2, category1);

        Category category3 = new Category(TEST_NAME_S, TEST_HAZARD_S, TEST_RARITY_S);
        category3.setId(TEST_ID_S);

        Category category4 = new Category(TEST_NAME_F, TEST_HAZARD_F, TEST_RARITY_F);
        category4.setId(TEST_ID_F);

        assertEquals(category2, category4);
        assertEquals(category1, category4);

        assertNotEquals(category1, category3);
        assertNotEquals(category1, null);
        assertNotEquals(category1, new Object());
    }

    @Test
    void hashCodeTest() {
        Category category1 = new Category(TEST_NAME_F, TEST_HAZARD_F, TEST_RARITY_F);
        category1.setId(TEST_ID_F);

        Category category2 = new Category(TEST_NAME_F, TEST_HAZARD_F, TEST_RARITY_F);
        category2.setId(TEST_ID_F);

        Category category3 = new Category(TEST_NAME_S, TEST_HAZARD_S, TEST_RARITY_S);
        category3.setId(TEST_ID_S);

        assertEquals(category1.hashCode(), category1.hashCode());
        assertEquals(category1.hashCode(), category2.hashCode());
        assertNotEquals(category1.hashCode(), category3.hashCode());
    }

    @Test
    void toStringTest() {
        Category category = new Category(TEST_NAME_F, TEST_HAZARD_F, TEST_RARITY_F);
        category.setId(TEST_ID_F);

        String toStringResult = category.toString();

        assertNotNull(toStringResult);
        assertTrue(toStringResult.contains(TEST_ID_F.toString()));
        assertTrue(toStringResult.contains(TEST_NAME_F));
        assertTrue(toStringResult.contains(TEST_HAZARD_F));
        assertTrue(toStringResult.contains(TEST_RARITY_F));
    }
}

package ru.goth.domain.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CityTest {

    private static final Long TEST_ID = 1L;
    private static final String TEST_NAME = "Moscow";
    private static final Long TEST_DELIVERY_TIME = 2L;
    private static final Long TEST_ID_AN = 2L;
    private static final String TEST_NAME_AN = "London";
    private static final Long TEST_DELIVERY_TIME_AN = 3L;

    @Test
    void getIdTest() {
        City city = new City();
        city.setId(TEST_ID);
        assertEquals(TEST_ID, city.getId());
    }

    @Test
    void setIdTest() {
        City city = new City();
        city.setId(TEST_ID);
        assertEquals(TEST_ID, city.getId());
    }

    @Test
    void getNameTest() {
        City city = new City(TEST_NAME, TEST_DELIVERY_TIME);
        assertEquals(TEST_NAME, city.getName());
    }

    @Test
    void setNameTest() {
        City city = new City();
        city.setName(TEST_NAME);
        assertEquals(TEST_NAME, city.getName());
    }

    @Test
    void getDeliveryTimeTest() {
        City city = new City(TEST_NAME, TEST_DELIVERY_TIME);
        assertEquals(TEST_DELIVERY_TIME, city.getDeliveryTime());
    }

    @Test
    void setDeliveryTimeTest() {
        City city = new City();
        city.setDeliveryTime(TEST_DELIVERY_TIME);
        assertEquals(TEST_DELIVERY_TIME, city.getDeliveryTime());
    }

    @Test
    void equalsTest() {
        City city1 = new City(TEST_NAME, TEST_DELIVERY_TIME);
        city1.setId(TEST_ID);

        City city2 = new City(TEST_NAME, TEST_DELIVERY_TIME);
        city2.setId(TEST_ID);

        City city3 = new City(TEST_NAME_AN, TEST_DELIVERY_TIME_AN);
        city3.setId(TEST_ID_AN);

        assertEquals(city1, city1);

        assertEquals(city1, city2);
        assertEquals(city2, city1);

        City city4 = new City(TEST_NAME, TEST_DELIVERY_TIME);
        city4.setId(TEST_ID);
        assertEquals(city2, city4);
        assertEquals(city1, city4);

        assertNotEquals(city1, city3);
        assertNotEquals(city1, null);
        assertNotEquals(city1, new Object());
    }

    @Test
    void hashCodeTest() {
        City city1 = new City(TEST_NAME, TEST_DELIVERY_TIME);
        city1.setId(TEST_ID);

        City city2 = new City(TEST_NAME, TEST_DELIVERY_TIME);
        city2.setId(TEST_ID);

        City city3 = new City(TEST_NAME_AN, TEST_DELIVERY_TIME_AN);
        city3.setId(TEST_ID_AN);

        assertEquals(city1.hashCode(), city1.hashCode());

        assertEquals(city1.hashCode(), city2.hashCode());

        assertNotEquals(city1.hashCode(), city3.hashCode());
    }

    @Test
    void toStringTest() {
        City city = new City(TEST_NAME, TEST_DELIVERY_TIME);
        city.setId(TEST_ID);

        String toStringResult = city.toString();

        assertNotNull(toStringResult);
        assertTrue(toStringResult.contains(TEST_ID.toString()));
        assertTrue(toStringResult.contains(TEST_NAME));
        assertTrue(toStringResult.contains(TEST_DELIVERY_TIME.toString()));
    }
}

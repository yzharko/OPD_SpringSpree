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
    void getId() {
        City city = new City();
        city.setId(TEST_ID);
        assertEquals(TEST_ID, city.getId());
    }

    @Test
    void setId() {
        City city = new City();
        city.setId(TEST_ID);
        assertEquals(TEST_ID, city.getId());
    }

    @Test
    void getName() {
        City city = new City(TEST_NAME, TEST_DELIVERY_TIME);
        assertEquals(TEST_NAME, city.getName());
    }

    @Test
    void setName() {
        City city = new City();
        city.setName(TEST_NAME);
        assertEquals(TEST_NAME, city.getName());
    }

    @Test
    void getDeliveryTime() {
        City city = new City(TEST_NAME, TEST_DELIVERY_TIME);
        assertEquals(TEST_DELIVERY_TIME, city.getDeliveryTime());
    }

    @Test
    void setDeliveryTime() {
        City city = new City();
        city.setDeliveryTime(TEST_DELIVERY_TIME);
        assertEquals(TEST_DELIVERY_TIME, city.getDeliveryTime());
    }

    @Test
    void testEquals() {
        City city1 = new City(TEST_NAME, TEST_DELIVERY_TIME);
        city1.setId(TEST_ID);

        City city2 = new City(TEST_NAME, TEST_DELIVERY_TIME);
        city2.setId(TEST_ID);

        City city3 = new City(TEST_NAME_AN, TEST_DELIVERY_TIME_AN);
        city3.setId(TEST_ID_AN);

        // Рефлексивность
        assertEquals(city1, city1);

        // Симметричность
        assertEquals(city1, city2);
        assertEquals(city2, city1);

        // Транзитивность
        City city4 = new City(TEST_NAME, TEST_DELIVERY_TIME);
        city4.setId(TEST_ID);
        assertEquals(city2, city4);
        assertEquals(city1, city4);

        // Неравенство
        assertNotEquals(city1, city3);
        assertNotEquals(city1, null);
        assertNotEquals(city1, new Object());
    }

    @Test
    void testHashCode() {
        City city1 = new City(TEST_NAME, TEST_DELIVERY_TIME);
        city1.setId(TEST_ID);

        City city2 = new City(TEST_NAME, TEST_DELIVERY_TIME);
        city2.setId(TEST_ID);

        City city3 = new City(TEST_NAME_AN, TEST_DELIVERY_TIME_AN);
        city3.setId(TEST_ID_AN);

        // Постоянство
        assertEquals(city1.hashCode(), city1.hashCode());

        // Равенство объектов -> равенство хэш-кодов
        assertEquals(city1.hashCode(), city2.hashCode());

        // Разные объекты (обычно) должны иметь разные хэш-коды
        assertNotEquals(city1.hashCode(), city3.hashCode());
    }

    @Test
    void testToString() {
        City city = new City(TEST_NAME, TEST_DELIVERY_TIME);
        city.setId(TEST_ID);

        String toStringResult = city.toString();

        assertNotNull(toStringResult);
        assertTrue(toStringResult.contains(TEST_ID.toString()));
        assertTrue(toStringResult.contains(TEST_NAME));
        assertTrue(toStringResult.contains(TEST_DELIVERY_TIME.toString()));
    }
}

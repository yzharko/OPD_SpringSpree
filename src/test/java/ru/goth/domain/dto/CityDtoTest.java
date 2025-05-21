package ru.goth.domain.dto;

import org.junit.jupiter.api.Test;

import static junit.framework.Assert.assertEquals;

class CityDtoTest {

    private static final Long TEST_ID = 1L;
    private static final String TEST_NAME = "Moscow";
    private static final Long TEST_DELIVERY_TIME = 2L;

    @Test
    void getSetIdTest() {
        CityDto cityDto = new CityDto();
        cityDto.setId(TEST_ID);
        assertEquals(TEST_ID, cityDto.getId());
    }

    @Test
    void getNameTest() {
        CityDto cityDto = new CityDto(TEST_NAME, TEST_DELIVERY_TIME);
        assertEquals(TEST_NAME, cityDto.getName());
    }

    @Test
    void setNameTest() {
        CityDto cityDto = new CityDto();
        cityDto.setName(TEST_NAME);
        assertEquals(TEST_NAME, cityDto.getName());
    }

    @Test
    void getDeliveryTimeTest() {
        CityDto cityDto = new CityDto(TEST_NAME, TEST_DELIVERY_TIME);
        assertEquals(TEST_DELIVERY_TIME, cityDto.getDeliveryTime());
    }

    @Test
    void setDeliveryTimeTest() {
        CityDto cityDto = new CityDto();
        cityDto.setDeliveryTime(TEST_DELIVERY_TIME);
        assertEquals(TEST_DELIVERY_TIME, cityDto.getDeliveryTime());
    }
}

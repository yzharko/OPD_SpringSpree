package ru.goth.domain.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StepDtoTest {

    private static final Long TEST_ID = 1L;
    private static final String TEST_NAME = "Step One";
    private static final String TEST_DESCRIPTION = "First step description";

    @Test
    void getSetIdTest() {
        StepDto stepDto = new StepDto();
        stepDto.setId(TEST_ID);
        assertEquals(TEST_ID, stepDto.getId());
    }

    @Test
    void getNameTest() {
        StepDto stepDto = new StepDto(TEST_NAME, TEST_DESCRIPTION);
        assertEquals(TEST_NAME, stepDto.getName());
    }

    @Test
    void setNameTest() {
        StepDto stepDto = new StepDto();
        stepDto.setName(TEST_NAME);
        assertEquals(TEST_NAME, stepDto.getName());
    }

    @Test
    void getDescriptionTest() {
        StepDto stepDto = new StepDto(TEST_NAME, TEST_DESCRIPTION);
        assertEquals(TEST_DESCRIPTION, stepDto.getDescription());
    }

    @Test
    void setDescriptionTest() {
        StepDto stepDto = new StepDto();
        stepDto.setDescription(TEST_DESCRIPTION);
        assertEquals(TEST_DESCRIPTION, stepDto.getDescription());
    }

    @Test
    void copyConstructorTest() {
        StepDto original = new StepDto(TEST_NAME, TEST_DESCRIPTION);
        original.setId(TEST_ID);

        StepDto copy = new StepDto(original);

        assertEquals(original.getId(), copy.getId());
        assertEquals(original.getName(), copy.getName());
        assertEquals(original.getDescription(), copy.getDescription());
    }
}

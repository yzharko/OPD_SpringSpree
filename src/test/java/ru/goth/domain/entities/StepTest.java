package ru.goth.domain.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StepTest {

    private static final Long TEST_ID = 1L;
    private static final String TEST_NAME = "Step One";
    private static final String TEST_DESCRIPTION = "First step description";
    private static final Long TEST_ID_AN = 2L;
    private static final String TEST_NAME_AN = "Step Two";
    private static final String TEST_DESCRIPTION_AN = "Second step description";

    @Test
    void getIdTest() {
        Step step = new Step();
        step.setId(TEST_ID);
        assertEquals(TEST_ID, step.getId());
    }

    @Test
    void setIdTest() {
        Step step = new Step();
        step.setId(TEST_ID);
        assertEquals(TEST_ID, step.getId());
    }

    @Test
    void getNameTest() {
        Step step = new Step(TEST_NAME, TEST_DESCRIPTION);
        assertEquals(TEST_NAME, step.getName());
    }

    @Test
    void setNameTest() {
        Step step = new Step();
        step.setName(TEST_NAME);
        assertEquals(TEST_NAME, step.getName());
    }

    @Test
    void getDescriptionTest() {
        Step step = new Step(TEST_NAME, TEST_DESCRIPTION);
        assertEquals(TEST_DESCRIPTION, step.getDescription());
    }

    @Test
    void setDescriptionTest() {
        Step step = new Step();
        step.setDescription(TEST_DESCRIPTION);
        assertEquals(TEST_DESCRIPTION, step.getDescription());
    }

    @Test
    void equalsTest() {
        Step step1 = new Step(TEST_NAME, TEST_DESCRIPTION);
        step1.setId(TEST_ID);

        Step step2 = new Step(TEST_NAME, TEST_DESCRIPTION);
        step2.setId(TEST_ID);

        Step step3 = new Step(TEST_NAME_AN, TEST_DESCRIPTION_AN);
        step3.setId(TEST_ID_AN);

        assertEquals(step1, step1);

        assertEquals(step1, step2);
        assertEquals(step2, step1);

        Step step4 = new Step(TEST_NAME, TEST_DESCRIPTION);
        step4.setId(TEST_ID);
        assertEquals(step2, step4);
        assertEquals(step1, step4);

        assertNotEquals(step1, step3);
        assertNotEquals(step1, null);
        assertNotEquals(step1, new Object());
    }

    @Test
    void hashCodeTest() {
        Step step1 = new Step(TEST_NAME, TEST_DESCRIPTION);
        step1.setId(TEST_ID);

        Step step2 = new Step(TEST_NAME, TEST_DESCRIPTION);
        step2.setId(TEST_ID);

        Step step3 = new Step(TEST_NAME_AN, TEST_DESCRIPTION_AN);
        step3.setId(TEST_ID_AN);

        assertEquals(step1.hashCode(), step1.hashCode());

        assertEquals(step1.hashCode(), step2.hashCode());

        assertNotEquals(step1.hashCode(), step3.hashCode());
    }

    @Test
    void toStringTest() {
        Step step = new Step(TEST_NAME, TEST_DESCRIPTION);
        step.setId(TEST_ID);

        String toStringResult = step.toString();

        assertNotNull(toStringResult);
        assertTrue(toStringResult.contains(TEST_ID.toString()));
        assertTrue(toStringResult.contains(TEST_NAME));
        assertTrue(toStringResult.contains(TEST_DESCRIPTION));
    }
}

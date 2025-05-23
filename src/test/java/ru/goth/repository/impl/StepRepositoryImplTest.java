package ru.goth.repository.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.goth.domain.dto.StepDto;
import ru.goth.repository.StepRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@Testcontainers
public class StepRepositoryImplTest {

    private static final String TEST_PARAMETER = "DB_Test";

    @Container
    private final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15.2")
            .withDatabaseName(TEST_PARAMETER)
            .withUsername(TEST_PARAMETER)
            .withPassword(TEST_PARAMETER);

    private Connection connection;
    private StepRepository stepRepository;

    @BeforeEach
    public void setUp() throws Exception {
        postgreSQLContainer.start();

        connection = DriverManager.getConnection(
                postgreSQLContainer.getJdbcUrl(),
                postgreSQLContainer.getUsername(),
                postgreSQLContainer.getPassword()
        );

        try (PreparedStatement statement = connection.prepareStatement(
                """
                CREATE TABLE step (
                    id SERIAL PRIMARY KEY,
                    name VARCHAR(50) NOT NULL,
                    description TEXT
                );
                """)) {
            statement.execute();
        }

        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM step")) {
            statement.execute();
        }

        stepRepository = new StepRepositoryImpl(connection);
    }

    @AfterEach
    public void tearDown() throws Exception {
        connection.close();
        postgreSQLContainer.stop();
    }

    @Test
    public void createStepTest() {
        StepDto createdStep = stepRepository.createStep(1L, "TEST_STEP", "Test description");

        assertNotNull(createdStep, "Созданный шаг не должен быть null");
        assertEquals(1L, createdStep.getId(), "ID созданного шага не совпадает");
        assertEquals("TEST_STEP", createdStep.getName(), "Название шага не совпадает");
        assertEquals("Test description", createdStep.getDescription(), "Описание не совпадает");
    }

    @Test
    public void getStepByIdTest() {
        stepRepository.createStep(1L, "TEST_STEP", "Test description");

        StepDto retrievedStep = stepRepository.getStepById(1L);

        assertNotNull(retrievedStep);
        assertEquals("TEST_STEP", retrievedStep.getName());
        assertEquals("Test description", retrievedStep.getDescription());
    }

    @Test
    public void getAllStepsTest() {
        stepRepository.createStep(1L, "TEST_STEP_1", "Description 1");
        stepRepository.createStep(2L, "TEST_STEP_2", "Description 2");

        List<StepDto> steps = stepRepository.getAllSteps();

        assertEquals(2, steps.size());
    }

    @Test
    public void updateStepTest() {
        stepRepository.createStep(1L, "TEST_STEP", "Old description");

        StepDto updatedStep = stepRepository.updateStep(1L, "UPDATED_STEP", "New description");

        assertEquals("UPDATED_STEP", updatedStep.getName());
        assertEquals("New description", updatedStep.getDescription());

        StepDto retrievedStep = stepRepository.getStepById(1L);
        assertEquals("UPDATED_STEP", retrievedStep.getName());
        assertEquals("New description", retrievedStep.getDescription());
    }

    @Test
    public void deleteStepTest() {
        stepRepository.createStep(1L, "TEST_STEP", "Test description");

        boolean isDeleted = stepRepository.deleteStep(1L);

        assertTrue(isDeleted);

        StepDto deletedStep = stepRepository.getStepById(1L);
        assertNull(deletedStep.getName());
        assertNull(deletedStep.getDescription());
    }

    @Test
    public void existStepTest() {
        stepRepository.createStep(1L, "TEST_STEP", "Test description");

        Long existingId = stepRepository.existStep("TEST_STEP");
        assertEquals(1L, existingId);

        Long nonExistingId = stepRepository.existStep("NON_EXISTING_STEP");
        assertNull(nonExistingId);
    }
}

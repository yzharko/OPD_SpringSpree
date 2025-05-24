package ru.goth.repository.impl;


import ru.goth.domain.dto.CategoryDto;
import ru.goth.repository.CategoryRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@Testcontainers
class CategoryRepositoryImplTest {

    private static final String TEST_PARAM = "DB_Test";

    @Container
    private final PostgreSQLContainer<?> postgreSQLContainer =
            new PostgreSQLContainer<>("postgres:15.2")
                    .withDatabaseName(TEST_PARAM)
                    .withUsername(TEST_PARAM)
                    .withPassword(TEST_PARAM);

    private Connection connection;
    private CategoryRepository categoryRepository;

    @BeforeEach
    public void setUp() throws Exception {
        postgreSQLContainer.start();

        connection = DriverManager.getConnection(
                postgreSQLContainer.getJdbcUrl(),
                postgreSQLContainer.getUsername(),
                postgreSQLContainer.getPassword()
        );

        try (PreparedStatement preStatement = connection.prepareStatement(
                """
                        CREATE TABLE category (
                            id SERIAL PRIMARY KEY,
                        	name VARCHAR(50),
                        	hazard VARCHAR(50),
                        	rarity VARCHAR(50)
                        );
                        """)) {
            preStatement.execute();
        }

        try (PreparedStatement preStatement = connection.prepareStatement("DELETE FROM category")) {
            preStatement.execute();
        }

        categoryRepository = new CategoryRepositoryImpl(connection);
    }
    @AfterEach
    public void tearDown() throws Exception {
        connection.close();
        postgreSQLContainer.stop();
    }
    @Test
    public void createCategoryTest() {
        CategoryDto createdCategory = categoryRepository.createCategory(
                1L,
                "TEST_CATEGORY",
                "TEST_HAZARD",
                "TEST_RARITY");

        assertNotNull(createdCategory, "The created category must not be null");
        assertEquals(1L, createdCategory.getId(), "The ID of the created category does not match");
        assertEquals("TEST_CATEGORY", createdCategory.getName(),
                "The name of the created category does not match");
        assertEquals("TEST_HAZARD", createdCategory.getHazard(),
                "The hazard of the created category does not match");
        assertEquals("TEST_RARITY", createdCategory.getRarity(),
                "The rarity of the created category does not match");
    }

    @Test
    public void getCategoryByIdTest() {
        categoryRepository.createCategory(
                1L,
                "TEST_CATEGORY",
                "TEST_HAZARD",
                "TEST_RARITY");
        CategoryDto retrievedCity = categoryRepository.getCategoryById(1L);

        assertNotNull(retrievedCity, "The retrieved city must not be null");
        assertEquals("TEST_CATEGORY", retrievedCity.getName(),
                "The name of the retrieved category does not match");
        assertEquals("TEST_HAZARD", retrievedCity.getHazard(),
                "The hazard of the retrieved category does not match");
        assertEquals("TEST_RARITY", retrievedCity.getRarity(),
                "The rarity of the retrieved category does not match");
    }

    @Test
    public void getAllCategoriesTest() {
        categoryRepository.createCategory(
                1L,
                "TEST_CATEGORY_1",
                "TEST_HAZARD_1",
                "TEST_RARITY_1");
        categoryRepository.createCategory(
                2L,
                "TEST_CATEGORY_2",
                "TEST_HAZARD_2",
                "TEST_RARITY_2");

        List<CategoryDto> retrievedCategories = categoryRepository.getAllCategories();

        assertEquals(2, retrievedCategories.size(),
                "The retrieved categories must have 2 elements");
    }

    @Test
    public void updateCategoryTest() {
        categoryRepository.createCategory(
                1L,
                "TEST_CATEGORY",
                "TEST_HAZARD",
                "TEST_RARITY");
        CategoryDto updatedCategory = categoryRepository.updateCategory(
                1L,
                "TEST_CATEGORY_NEW",
                "TEST_HAZARD_NEW",
                "TEST_RARITY_NEW");

        assertEquals("TEST_CATEGORY_NEW", updatedCategory.getName(),
                "The updated category name does not match");
        assertEquals("TEST_HAZARD_NEW", updatedCategory.getHazard(),
                "The updated category does not match");
        assertEquals("TEST_RARITY_NEW", updatedCategory.getRarity(),
                "The updated category does not match");

        CategoryDto retrievedCategory = categoryRepository.getCategoryById(1L);
        assertEquals("TEST_CATEGORY_NEW", retrievedCategory.getName(),
                "The updated category name does not match");
    }

    @Test
    public void deleteCategoryTest() throws SQLException {
        categoryRepository.createCategory(
                1L,
                "TEST_CATEGORY",
                "TEST_HAZARD",
                "TEST_RARITY");
        boolean deleted = categoryRepository.deleteCategory(1L);
        assertTrue(deleted,
                "The deleted category must not be null");
    }

    @Test
    public void existCategoryTest() {
        categoryRepository.createCategory(
                1L,
                "TEST_CATEGORY",
                "TEST_HAZARD",
                "TEST_RARITY");
        Long existedId = categoryRepository.existCategory("TEST_CATEGORY");
        assertEquals(1L, existedId,
                "The ID of the existed category does not match");

        Long nonExistedId = categoryRepository.existCategory("TEST_CATEGORY_NON");
        assertNull(nonExistedId, "The ID of the non existed category does not match");
    }
}

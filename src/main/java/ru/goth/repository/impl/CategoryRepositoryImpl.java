package ru.goth.repository.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import ru.goth.domain.dto.CategoryDto;
import ru.goth.domain.entities.Category;
import ru.goth.config.DBconfig;
import ru.goth.domain.mappers.CategoryMapper;
import ru.goth.domain.mappers.CategoryMapperImpl;

import java.sql.Connection;

import ru.goth.repository.CategoryRepository;

import static ru.goth.constants.RepositoryConstants.ERROR_IN_CREATE;
import static ru.goth.constants.RepositoryConstants.ERROR_IN_READ_BY_ID;
import static ru.goth.constants.RepositoryConstants.ERROR_IN_READ_ALL;
import static ru.goth.constants.RepositoryConstants.ERROR_IN_UPDATE;
import static ru.goth.constants.RepositoryConstants.ERROR_IN_DELETE;
import static ru.goth.constants.RepositoryConstants.ERROR_IN_EXIST;
import static ru.goth.constants.RepositoryConstants.ROWS_UPDATED;
import static ru.goth.constants.RepositoryConstants.ROWS_ADDED;

public class CategoryRepositoryImpl implements CategoryRepository {

    Logger logger = Logger.getLogger(getClass().getName());
    private final CategoryMapper categoryMapper = new CategoryMapperImpl();
    private final Connection connection;

    public CategoryRepositoryImpl() {
        this.connection = DBconfig.getConnection();
    }

    public CategoryRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public CategoryDto createCategory(Long id, String name, String hazard, String rarity) {
        try (PreparedStatement statement = this.connection.prepareStatement(
                     "INSERT INTO category (name, hazard, rarity) VALUES (?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS)) {

            Category category = new Category(name, hazard, rarity);
            category.setId(id);

            statement.setString(1, category.getName());
            statement.setString(2, category.getHazard());
            statement.setString(3, category.getRarity());

            int rowsAffected = statement.executeUpdate();
            logger.info(ROWS_ADDED + rowsAffected);
            return categoryMapper.toCategoryDto(category);

        } catch (SQLException e) {
            logger.log(Level.SEVERE, ERROR_IN_CREATE, e);
            return null;
        }
    }

    @Override
    public CategoryDto getCategoryById(Long id) {
        try (PreparedStatement statement = this.connection.prepareStatement(
                     "SELECT id, name, hazard, rarity FROM category WHERE id = ?")) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            Category category = new Category();
            if (resultSet.next()) {
                category.setId(resultSet.getLong("id"));
                category.setName(resultSet.getString("name"));
                category.setHazard(resultSet.getString("hazard"));
                category.setRarity(resultSet.getString("rarity"));
            }
            return categoryMapper.toCategoryDto(category);

        } catch (SQLException e) {
            logger.log(Level.SEVERE, ERROR_IN_READ_BY_ID, e);
            return null;
        }
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        try (PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM category");
             ResultSet rs = statement.executeQuery()) {

            List<CategoryDto> categories = new ArrayList<>();
            while (rs.next()) {
                Category category = new Category();
                category.setId(rs.getLong("id"));
                category.setName(rs.getString("name"));
                category.setHazard(rs.getString("hazard"));
                category.setRarity(rs.getString("rarity"));
                categories.add(categoryMapper.toCategoryDto(category));
            }
            return categories;

        } catch (SQLException e) {
            logger.log(Level.SEVERE, ERROR_IN_READ_ALL, e);
            return Collections.emptyList();
        }
    }

    @Override
    public CategoryDto updateCategory(Long id, String name, String hazard, String rarity) {
        try (PreparedStatement statement = this.connection.prepareStatement(
                     "UPDATE category SET name = ?, hazard = ?, rarity = ? WHERE id = ?")) {

            Category category = new Category(name, hazard, rarity);
            category.setId(id);

            statement.setString(1, category.getName());
            statement.setString(2, category.getHazard());
            statement.setString(3, category.getRarity());
            statement.setLong(4, category.getId());

            int rowsAffected = statement.executeUpdate();
            logger.info(ROWS_UPDATED + rowsAffected);
            return categoryMapper.toCategoryDto(category);

        } catch (SQLException e) {
            logger.log(Level.SEVERE, ERROR_IN_UPDATE, e);
            return null;
        }
    }

    @Override
    public boolean deleteCategory(Long id) {
        try (PreparedStatement statement = this.connection.prepareStatement(
                     "DELETE FROM category WHERE id = ?")) {

            statement.setLong(1, id);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            logger.log(Level.SEVERE, ERROR_IN_DELETE, e);
            return false;
        }
    }

    @Override
    public Long existCategory(String name) {
        try (PreparedStatement statement = this.connection.prepareStatement(
                     "SELECT id FROM category WHERE name = ?")) {

            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return rs.getLong("id");
            }
            return null;

        } catch (SQLException e) {
            logger.log(Level.SEVERE, ERROR_IN_EXIST, e);
            return null;
        }
    }
}

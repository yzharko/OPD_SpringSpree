package ru.goth.repository.impl;

import ru.goth.config.DBconfig;
import ru.goth.domain.dto.StepDto;
import ru.goth.domain.entities.Step;
import ru.goth.domain.mappers.StepMapper;
import ru.goth.domain.mappers.StepMapperImpl;
import ru.goth.repository.StepRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

import static ru.goth.constants.RepositoryConstants.*;

public class StepRepositoryImpl implements StepRepository {

    Logger logger = Logger.getLogger(StepRepositoryImpl.class.getName());
    private final StepMapper stepMapper = new StepMapperImpl();
    private final Connection connection;

    public StepRepositoryImpl() {
        this.connection = DBconfig.getConnection();
    }

    public StepRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public StepDto createStep(Long id, String name, String description) {
        try (PreparedStatement statement = this.connection.prepareStatement(
                     "INSERT INTO step (name, description) VALUES (?, ?)")) {

            Step step = new Step(name, description);
            step.setId(id);
            statement.setString(1, step.getName());
            statement.setString(2, step.getDescription());

            int rowsAffected = statement.executeUpdate();
            logger.info(ROWS_ADDED + rowsAffected);

            return stepMapper.toStepDto(step);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, ERROR_IN_CREATE, e);
            return null;
        }
    }

    @Override
    public StepDto getStepById(Long id) {
        try (PreparedStatement statement = this.connection.prepareStatement(
                     "SELECT id, name, description FROM step WHERE id = ?")) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            Step step = new Step();
            while (resultSet.next()) {
                step.setId(resultSet.getLong("id"));
                step.setName(resultSet.getString("name"));
                step.setDescription(resultSet.getString("description"));
            }
            return stepMapper.toStepDto(step);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, ERROR_IN_READ_BY_ID, e);
            return null;
        }
    }

    @Override
    public List<StepDto> getAllSteps() {
        logger.info("Getting all steps");
        try (PreparedStatement statement = this.connection.prepareStatement(
                     "SELECT * FROM step");
             ResultSet resultSet = statement.executeQuery()) {

            List<StepDto> steps = new ArrayList<>();
            while (resultSet.next()) {
                Step step = new Step();
                step.setId(resultSet.getLong("id"));
                step.setName(resultSet.getString("name"));
                step.setDescription(resultSet.getString("description"));
                steps.add(stepMapper.toStepDto(step));
            }
            return steps;
        } catch (SQLException e) {
                 logger.log(Level.SEVERE, ERROR_IN_READ_ALL, e);
                 return Collections.emptyList();
        }
    }

    @Override
    public StepDto updateStep(Long id, String name, String description) {
        try (PreparedStatement statement = this.connection.prepareStatement(
                    "UPDATE step SET name = ?, description = ? WHERE id = ?")) {
            Step step = new Step(name, description);
            step.setId(id);

            statement.setString(1, step.getName());
            statement.setString(2, step.getDescription());
            statement.setLong(3, step.getId());
            int rowsAffected = statement.executeUpdate();
            logger.info(ROWS_UPDATED + rowsAffected);
            return stepMapper.toStepDto(step);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, ERROR_IN_UPDATE, e);
            return null;
        }
    }

    @Override
    public boolean deleteStep(Long id) {
        try (PreparedStatement statement = this.connection.prepareStatement(
                "DELETE FROm step WHERE id = ?")) {
            statement.setLong(1, id);
            int rowAffected = statement.executeUpdate();
            return rowAffected > 0;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, ERROR_IN_DELETE, e);
        }
        return false;
    }

    @Override
    public Long existStep(String name) {
        Long id = null;
        try (PreparedStatement statement = this.connection.prepareStatement(
                "SELECT id FROM step WHERE name = ?")) {

            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                id = resultSet.getLong("id");
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, ERROR_IN_EXIST, e);
        }
        return id;
    }
}

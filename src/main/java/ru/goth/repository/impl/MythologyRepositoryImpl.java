package ru.goth.repository.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import ru.goth.domain.dto.MythologyDto;
import ru.goth.domain.entities.Mythology;
import ru.goth.config.DBconfig;
import ru.goth.domain.mappers.MythologyMapper;
import ru.goth.domain.mappers.MythologyMapperImpl;

import java.sql.Connection;

import ru.goth.repository.MythologyRepository;

import static ru.goth.constants.RepositoryConstants.*;

public class MythologyRepositoryImpl implements MythologyRepository {

    Logger logger = Logger.getLogger(getClass().getName());
    private final MythologyMapper mythologyMapper = new MythologyMapperImpl();

    @Override
    public MythologyDto createMythology(Long id, String name) {
        try (Connection con = DBconfig.getConnection();
             PreparedStatement statement = con.prepareStatement(
                     "INSERT INTO mythology (name) VALUES (?)")) {

            Mythology mythology = new Mythology(name);
            mythology.setId(id);
            statement.setString(1, mythology.getName());

            int rowsAffected = statement.executeUpdate();
            logger.info(ROWS_ADDED + rowsAffected);
            return mythologyMapper.toMythologyDto(mythology);

        } catch (SQLException e) {
            logger.log(Level.SEVERE, ERROR_IN_CREATE, e);
            return null;
        }
    }

    @Override
    public MythologyDto getMythologyById(Long id) {
        try (Connection con = DBconfig.getConnection();
             PreparedStatement statement = con.prepareStatement(
                     "SELECT id, name FROM mythology WHERE id = ?")) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            Mythology mythology = new Mythology();
            while (resultSet.next()) {
                mythology.setId(resultSet.getLong("id"));
                mythology.setName(resultSet.getString("name"));
            }
            return mythologyMapper.toMythologyDto(mythology);

        } catch (SQLException e) {
            logger.log(Level.SEVERE, ERROR_IN_READ_BY_ID, e);
            return null;
        }
    }

    @Override
    public List<MythologyDto> getAllMythologies() {
        try (Connection con = DBconfig.getConnection();
             PreparedStatement statement = con.prepareStatement(
                     "SELECT * FROM mythology");
             ResultSet rs = statement.executeQuery()) {

            List<MythologyDto> mythologies = new ArrayList<>();
            while (rs.next()) {
                Mythology mythology = new Mythology();
                mythology.setId(rs.getLong("id"));
                mythology.setName(rs.getString("name"));
                mythologies.add(mythologyMapper.toMythologyDto(mythology));
            }
            return mythologies;

        } catch (SQLException e) {
            logger.log(Level.SEVERE, ERROR_IN_READ_ALL, e);
            return Collections.emptyList();
        }
    }

    @Override
    public MythologyDto updateMythology(Long id, String name) {
        try (Connection conn = DBconfig.getConnection();
             PreparedStatement statement = conn.prepareStatement(
                     "UPDATE mythology SET name = ? WHERE id = ?")) {

            Mythology mythology = new Mythology(name);
            mythology.setId(id);
            statement.setString(1, mythology.getName());
            statement.setLong(2, mythology.getId());

            int rowsAffected = statement.executeUpdate();
            logger.info(ROWS_UPDATED + rowsAffected);
            return mythologyMapper.toMythologyDto(mythology);

        } catch (SQLException e) {
            logger.log(Level.SEVERE, ERROR_IN_UPDATE, e);
            return null;
        }
    }

    @Override
    public boolean deleteMythology(Long id) {
        try (Connection conn = DBconfig.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(
                     "DELETE FROM mythology WHERE id = ?")) {

            preparedStatement.setLong(1, id);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            logger.log(Level.SEVERE, ERROR_IN_UPDATE, e);
        }
        return false;
    }

    @Override
    public Long existMythology(String name) {
        Long id = null;
        try (Connection conn = DBconfig.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(
                     "SELECT id FROM mythology WHERE name = ?")) {

            preparedStatement.setString(1, name);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                id = rs.getLong("id");
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, ERROR_IN_EXIST, e);
        }
        return id;
    }
}

package com.example.repository.impl;

import com.example.db.ConnectionManagerImpl;
import com.example.db.IConnectionManager;
import com.example.exeption.RepositoryException;
import com.example.model.ClassRoom;
import com.example.repository.ClassRoomRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClassRoomRepositoryImpl implements ClassRoomRepository {
    private static ClassRoomRepositoryImpl instance;
    private static final IConnectionManager connectionManager = ConnectionManagerImpl.getInstance();

    private ClassRoomRepositoryImpl() {
    }

    public static ClassRoomRepository getInstance() {
        if (instance == null) {
            instance = new ClassRoomRepositoryImpl();
        }

        return instance;
    }

    @Override
    public ClassRoom add(ClassRoom classRoom) {
        String addSql = "INSERT INTO class_rooms (code) VALUES (?);";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(addSql, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, classRoom.getCode());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                classRoom = new ClassRoom(resultSet.getLong("id"), classRoom.getCode());
            }

        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
        return classRoom;
    }

    @Override
    public void update(ClassRoom classRoom) {
        String updateSql = "UPDATE class_rooms SET code=? WHERE id=?;";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateSql)) {

            preparedStatement.setString(1, classRoom.getCode());
            preparedStatement.setLong(2, classRoom.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    public boolean deleteById(Long id) {
        boolean result;
        String deleteSql = "DELETE FROM class_rooms WHERE id = ?;";
        try (Connection connection = connectionManager.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(deleteSql)) {

            preparedStatement.setLong(1, id);
            result = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }

        return result;
    }

    @Override
    public ClassRoom getById(Long id) {
        String findByIdSql = "SELECT id, code FROM class_rooms WHERE id=?;";
        ClassRoom classRoom = null;
        IConnectionManager connectionManager = ConnectionManagerImpl.getInstance();

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(findByIdSql);) {

            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                classRoom = createClassRoom(resultSet);
            }
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }

        return classRoom;
    }

    @Override
    public List<ClassRoom> getAll() {
        String getAllSql = "SELECT id, code FROM class_rooms;";
        List<ClassRoom> learnerList = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getAllSql)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                learnerList.add(createClassRoom(resultSet));
            }
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }

        return learnerList;
    }

    private ClassRoom createClassRoom(ResultSet resultSet) throws SQLException {
        return new ClassRoom(
                resultSet.getLong("id"),
                resultSet.getString("code")
        );
    }
}

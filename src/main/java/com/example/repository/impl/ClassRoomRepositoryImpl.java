package com.example.repository.impl;

import com.example.db.ConnectionManagerImpl;
import com.example.db.IConnectionManager;
import com.example.exeption.RepositoryException;
import com.example.model.ClassRoom;
import com.example.repository.ClassRoomRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ClassRoomRepositoryImpl implements ClassRoomRepository {
    private static ClassRoomRepositoryImpl instance;

    private ClassRoomRepositoryImpl() {
    }

    public static ClassRoomRepository getInstance() {
        if (instance == null) {
            instance = new ClassRoomRepositoryImpl();
        }

        return instance;
    }

    @Override
    public ClassRoom add(ClassRoom entity) {
        return null;
    }

    @Override
    public void update(ClassRoom entity) { // Добавить реализацию метода

    }

    @Override
    public boolean deleteById(Long id) {
        return false;
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
        return List.of();
    }

    private ClassRoom createClassRoom(ResultSet resultSet) throws SQLException {
        return new ClassRoom(
                resultSet.getLong("id"),
                resultSet.getString("code")
        );
    }
}

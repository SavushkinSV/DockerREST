package com.example.repository.impl;

import com.example.db.ConnectionDB;
import com.example.models.Learner;
import com.example.repository.IRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class LearnerRepositoryImpl implements IRepository<Learner, Long> {
    private static LearnerRepositoryImpl instance;
    private final ConnectionDB connectionDB = ConnectionDB.getInstance();

    private LearnerRepositoryImpl() {
    }

    public static LearnerRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new LearnerRepositoryImpl();
        }

        return instance;
    }

    @Override
    public Learner getById(Long id) {
        Learner leaner = null;
        String FIND_BY_ID_SQL = "SELECT learner_id, first_name, last_name, class_code FROM learners WHERE learner_id = ?";
        try (Connection connection = connectionDB.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                leaner = createLearner(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return leaner;
    }

    @Override
    public void add(Learner entity) {

    }

    @Override
    public void update(Learner entity) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<Learner> getAll() {
        return List.of();
    }

    private Learner createLearner(ResultSet resultSet) throws SQLException {
        Long learnerId = resultSet.getLong("learner_id");

        return new Learner(
                learnerId,
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                resultSet.getString("class_code"),
                null
        );
    }
}

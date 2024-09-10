package com.example.repository.impl;

import com.example.db.ConnectionManagerImpl;
import com.example.db.IConnectionManager;
import com.example.exception.RepositoryException;
import com.example.model.Learner;
import com.example.model.Rating;
import com.example.repository.LearnerRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LearnerRepositoryImpl implements LearnerRepository {
    private static LearnerRepositoryImpl instance;
    private final IConnectionManager connectionDB = ConnectionManagerImpl.getInstance();

    private LearnerRepositoryImpl() {
    }

    /**
     * Возвращает единственный экземпляр объекта класса {@code LearnerRepositoryImpl}.
     *
     * @return экземпляр объекта
     */
    public static synchronized LearnerRepository getInstance() {
        if (instance == null) {
            instance = new LearnerRepositoryImpl();
        }

        return instance;
    }

    /**
     * Добавляет в базу данных сущность ученик.
     * 1. Сохраняет ученика
     * 2. Сохраняет класс, в котором учится ученик
     * 3. Сохраняет список предметов
     *
     * @param learner сущность ученик
     * @return сущность ученик
     */
    @Override
    public Learner add(Learner learner) {
        String ADD_SQL = "INSERT INTO learners (first_name, last_name, class_code) VALUES (?, ?, ?);";
        try (Connection connection = connectionDB.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_SQL, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, learner.getFirstName());
            preparedStatement.setString(2, learner.getLastName());
            if (learner.getClassRoom() == null) {
                preparedStatement.setNull(3, Types.NULL);
            } else {
                preparedStatement.setString(3, learner.getClassRoom().getClassCode());
            }
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                learner = new Learner(
                        resultSet.getLong("learner_id"),
                        learner.getFirstName(),
                        learner.getLastName(),
                        learner.getClassRoom(),
                        null
                        );
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }


        return learner;
    }

    /**
     * Сохраняет список оценок.
     *
     *
     * @param learner объект ученик
     */
    private void saveRatingList(Learner learner) {
        if (learner.getRatingList() != null && !learner.getRatingList().isEmpty()) {
            List<Long> ratingList = new ArrayList<>(
                    learner.getRatingList()
                            .stream()
                            .map(Rating::getId)
                            .toList()
            );
        }
    }

    @Override
    public Optional<Learner> getById(Long id) {
        String FIND_BY_ID_SQL = "SELECT learner_id, first_name, last_name, class_code FROM learners WHERE learner_id = ?";
        Learner leaner = null;
        try (Connection connection = connectionDB.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                leaner = createLearner(resultSet);
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }

        return Optional.ofNullable(leaner);
    }


    @Override
    public void update(Learner entity) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<Learner> getAll() {
        String FIND_ALL_SQL = "SELECT learner_id, first_name, last_name, class_code FROM learners";
        List<Learner> learnerList = new ArrayList<>();
        try (Connection connection = connectionDB.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                learnerList.add(createLearner(resultSet));
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }

        return learnerList;
    }

    /**
     * Создает новую сущность учение по запросу.
     * @param resultSet результат запроса
     * @return сущность учение
     * @throws SQLException
     */
    private Learner createLearner(ResultSet resultSet) throws SQLException {
        Long learnerId = resultSet.getLong("learner_id");

        return new Learner(
                learnerId,
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                null,
                null
        );
    }

}

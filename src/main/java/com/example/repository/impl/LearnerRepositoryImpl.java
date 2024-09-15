package com.example.repository.impl;

import com.example.db.ConnectionManagerImpl;
import com.example.db.IConnectionManager;
import com.example.model.Learner;
import com.example.model.Rating;
import com.example.repository.LearnerRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LearnerRepositoryImpl implements LearnerRepository {
    private static LearnerRepositoryImpl instance;
    private static final IConnectionManager connectionManager = ConnectionManagerImpl.getInstance();

    private LearnerRepositoryImpl() {
    }

    /**
     * Возвращает единственный экземпляр объекта класса {@code LearnerRepositoryImpl}.
     *
     * @return экземпляр объекта
     */
    public static LearnerRepository getInstance() {
        if (instance == null) {
            instance = new LearnerRepositoryImpl();
        }

        return instance;
    }

    /**
     * Добавляет в базу данных сущность.
     *
     * @param learner сущность
     * @return сущность
     */
    @Override
    public Learner add(Learner learner) {
        String ADD_SQL = "INSERT INTO learners (first_name, last_name, class_id) VALUES (?, ?, ?);";
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_SQL, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, learner.getFirstName());
            preparedStatement.setString(2, learner.getLastName());
            if (learner.getClassRoom() == null) {
                preparedStatement.setNull(3, Types.NULL);
            } else {
                preparedStatement.setLong(3, learner.getClassRoom().getId());
            }
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                learner = new Learner(
                        resultSet.getLong("id"),
                        learner.getFirstName(),
                        learner.getLastName(),
                        learner.getClassRoom(),
                        null
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return learner;
    }

    /**
     * Сохраняет список оценок.
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
    public Learner getById(Long id) {
        String FIND_BY_ID_SQL = "SELECT id, first_name, last_name, class_id FROM learners WHERE id=?;";
        Learner leaner = null;
        try (Connection connection = connectionManager.getConnection()) {
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

    /**
     * Обновляет сущность.
     *
     * @param learner сущность
     */
    @Override
    public void update(Learner learner) {
        String UPDATE_SQL = "UPDATE learners SET first_name=?, last_name=?, class_id=? WHERE id=?;";
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL);
            preparedStatement.setString(1, learner.getFirstName());
            preparedStatement.setString(2, learner.getLastName());
            if (learner.getClassRoom() == null) {
                preparedStatement.setNull(3, Types.NULL);
            } else {
                preparedStatement.setString(3, learner.getClassRoom().getClassCode());
            }
            preparedStatement.setLong(4, learner.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Удаляет сущность по идентификатору.
     *
     * @param id идентификатор
     */
    @Override
    public boolean deleteById(Long id) {
        boolean result = false;
        String DELETE_SQL = "DELETE FROM learners WHERE id = ?;";
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL);
            preparedStatement.setLong(1, id);

            result = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    /**
     * Возвращает список всех сущностей.
     *
     * @return список сущностей
     */
    @Override
    public List<Learner> getAll() {
        String FIND_ALL_SQL = "SELECT id, first_name, last_name, class_id FROM learners;";
        List<Learner> learnerList = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                learnerList.add(createLearner(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return learnerList;
    }

    /**
     * Создает новую сущность по запросу.
     *
     * @param resultSet результат запроса
     * @return сущность
     * @throws SQLException
     */
    private static Learner createLearner(ResultSet resultSet) throws SQLException {
        Long learnerId = resultSet.getLong("id");

        return new Learner(
                learnerId,
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                null,
                null
        );
    }
}

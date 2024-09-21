package com.example.repository.impl;

import com.example.db.ConnectionManagerImpl;
import com.example.db.IConnectionManager;
import com.example.exeption.RepositoryException;
import com.example.model.ClassRoom;
import com.example.model.Learner;
import com.example.model.Rating;
import com.example.repository.ClassRoomRepository;
import com.example.repository.LearnerRepository;
import com.example.repository.RatingRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LearnerRepositoryImpl implements LearnerRepository {
    private static LearnerRepositoryImpl instance;
    private static final IConnectionManager connectionManager = ConnectionManagerImpl.getInstance();
    private static final ClassRoomRepository classRoomRepository = ClassRoomRepositoryImpl.getInstance();
    private static final RatingRepository ratingRepository = RatingRepositoryImpl.getInstance();

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
        String addSql = "INSERT INTO learners (first_name, last_name, class_id) VALUES (?, ?, ?);";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(addSql, Statement.RETURN_GENERATED_KEYS)) {


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
            throw new RepositoryException(e.getMessage());
        }

        return learner;
    }

    /**
     * Возвращает сущность по идентификатору.
     *
     * @param id идентификатор
     * @return сущность
     */
    @Override
    public Learner getById(Long id) {
        final String FIND_BY_ID_SQL = "SELECT id, first_name, last_name, class_id FROM learners WHERE id=?;";
        Learner leaner = null;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {

            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                leaner = createLearner(resultSet);
            }
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
        if (leaner != null) {
            leaner.setRatingList(saveRatingList(leaner.getId()));
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
        String updateSql = "UPDATE learners SET first_name=?, last_name=?, class_id=? WHERE id=?;";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateSql)) {

            preparedStatement.setString(1, learner.getFirstName());
            preparedStatement.setString(2, learner.getLastName());
            if (learner.getClassRoom() == null) {
                preparedStatement.setNull(3, Types.NULL);
            } else {
                preparedStatement.setLong(3, learner.getClassRoom().getId());
            }
            preparedStatement.setLong(4, learner.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    /**
     * Удаляет сущность по идентификатору.
     * Возвращает {@code true} если сущность удалена. Возвращает {@code false} если сущность не найдена.
     *
     * @param id идентификатор
     * @return {@code true} если сущность удалена
     */
    @Override
    public boolean deleteById(Long id) {
        boolean result;
        String deleteSql = "DELETE FROM learners WHERE id = ?;";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteSql)) {

            preparedStatement.setLong(1, id);

            result = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
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
        String getAllSql = "SELECT id, first_name, last_name, class_id FROM learners;";
        List<Learner> learnerList = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getAllSql)) {


            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                learnerList.add(createLearner(resultSet));
            }
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
        if (!learnerList.isEmpty()) {
            learnerList.stream().forEach(x -> x.setRatingList(saveRatingList(x.getId())));
        }

        return learnerList;
    }

    /**
     * Создает новую сущность по запросу.
     *
     * @param resultSet результат запроса
     * @return сущность
     */
    private static Learner createLearner(ResultSet resultSet) throws SQLException {
        Long learnerId = resultSet.getLong("id");
        ClassRoom classRoom = classRoomRepository.getById(resultSet.getLong("class_id"));

        return new Learner(
                learnerId,
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                classRoom,
                null
        );
    }

    /**
     * Сохраняет список оценок сущности Learner по идентификатору.
     *
     * @param id идентификатор
     */
    private List<Rating> saveRatingList(Long id) {
        List<Rating> ratingList = new ArrayList<>();
        final String FIND_RATINGS_BY_ID_SQL = "SELECT r.id, data, value, subject_name FROM ratings r JOIN learner_ratings lr ON r.id=lr.rating_id WHERE lr.learner_id=?;";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_RATINGS_BY_ID_SQL)) {

            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Long ratingId = resultSet.getLong("id");
                ratingList.add(ratingRepository.getById(ratingId));
            }
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }

        return ratingList;
    }
}

package com.example.repository.impl;

import com.example.db.ConnectionManagerImpl;
import com.example.db.IConnectionManager;
import com.example.exeption.RepositoryException;
import com.example.model.Rating;
import com.example.repository.RatingRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RatingRepositoryImpl implements RatingRepository {
    private static RatingRepositoryImpl instance;
    private static final IConnectionManager connectionManager = ConnectionManagerImpl.getInstance();

    private RatingRepositoryImpl() {
    }

    /**
     * Возвращает единственный экземпляр объекта класса {@code RatingRepositoryImpl}.
     *
     * @return экземпляр класса
     */
    public static RatingRepository getInstance() {
        if (instance == null) {
            instance = new RatingRepositoryImpl();
        }

        return instance;
    }

    /**
     * Добавляет в базу данных сущность.
     *
     * @param rating сущность
     * @return сущность
     */
    @Override
    public Rating add(Rating rating) {
        String addSql = "INSERT INTO ratings (data, value, subject_name) VALUES (?, ?, ?);";

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(addSql, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setDate(1, Date.valueOf(rating.getDate()));
            preparedStatement.setInt(2, rating.getValue());
            preparedStatement.setString(3, rating.getSubjectName());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                rating = new Rating(
                        resultSet.getLong("id"),
                        rating.getDate(),
                        rating.getValue(),
                        rating.getSubjectName()
                );
            }
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }

        return rating;
    }

    /**
     * Обновляет сущность.
     *
     * @param rating сущность
     */
    @Override
    public void update(Rating rating) {
        String updateSql = "UPDATE ratings SET data=?, value=?, subject_name=? WHERE id=?;";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateSql)) {

            preparedStatement.setDate(1, Date.valueOf(rating.getDate()));
            preparedStatement.setInt(2, rating.getValue());
            preparedStatement.setString(3, rating.getSubjectName());
            preparedStatement.setLong(4, rating.getId());
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
        boolean result = false;
        String deleteSql = "DELETE FROM ratings WHERE id = ?;";
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
     * Возвращает сущность по идентификатору.
     *
     * @param id идентификатор
     * @return сущность
     */
    @Override
    public Rating getById(Long id) {
        String getByIdSql = "SELECT id, data, value, subject_name FROM ratings WHERE id=?;";
        Rating rating = null;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getByIdSql);) {

            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                rating = createRating(resultSet);
            }
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }

        return rating;
    }

    /**
     * Возвращает список всех сущностей.
     *
     * @return список сущностей
     */
    @Override
    public List<Rating> getAll() {
        String getAllSql = "SELECT id, data, value, subject_name FROM ratings;";
        List<Rating> ratingList = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getAllSql)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ratingList.add(createRating(resultSet));
            }
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }

        return ratingList;
    }

    /**
     * Создает новую сущность по запросу.
     *
     * @param resultSet результат запроса
     * @return сущность
     * @throws SQLException
     */
    private static Rating createRating(ResultSet resultSet) throws SQLException {
        Long ratingId = resultSet.getLong("id");

        return new Rating(
                ratingId,
                resultSet.getString("data"),
                resultSet.getInt("value"),
                resultSet.getString("subject_name")
        );
    }

    @Override
    public List<Rating> getAllByLearnerId(Long learnerId) {
        List<Rating> ratingList = new ArrayList<>();
        String getAllByLearnerIdSql = "SELECT r.id, data, value, subject_name FROM ratings r JOIN learner_ratings lr ON r.id=lr.rating_id WHERE lr.learner_id=?;";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getAllByLearnerIdSql)) {

            preparedStatement.setLong(1, learnerId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ratingList.add(createRating(resultSet));
            }
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }

        return ratingList;
    }
}

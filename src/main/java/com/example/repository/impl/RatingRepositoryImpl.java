package com.example.repository.impl;

import com.example.db.ConnectionManagerImpl;
import com.example.db.IConnectionManager;
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
        String ADD_SQL = "INSERT INTO ratings (data, value, subject_name) VALUES (?, ?, ?);";


        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_SQL, Statement.RETURN_GENERATED_KEYS);

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
            throw new RuntimeException(e);
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
        String UPDATE_SQL = "UPDATE ratings SET data=?, value=?, subject_name=? WHERE id=?;";
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL);
            preparedStatement.setDate(1, Date.valueOf(rating.getDate()));
            preparedStatement.setInt(2, rating.getValue());
            preparedStatement.setString(3, rating.getSubjectName());
            preparedStatement.setLong(4, rating.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
        String DELETE_SQL = "DELETE FROM ratings WHERE id = ?;";
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
     * Возвращает сущность по идентификатору.
     *
     * @param id идентификатор
     * @return сущность
     */
    @Override
    public Rating getById(Long id) {
        String FIND_BY_ID_SQL = "SELECT id, data, value, subject_name FROM ratings WHERE id=?;";
        Rating rating = null;
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                rating = createRating(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
        String FIND_ALL_SQL = "SELECT id, data, value, subject_name FROM ratings;";
        List<Rating> ratingList = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ratingList.add(createRating(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
}

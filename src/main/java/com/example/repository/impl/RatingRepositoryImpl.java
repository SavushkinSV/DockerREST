package com.example.repository.impl;

import com.example.db.ConnectionManagerImpl;
import com.example.db.IConnectionManager;
import com.example.model.Learner;
import com.example.model.Rating;
import com.example.repository.RatingRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    @Override
    public Rating add(Rating entity) {
        return null;
    }

    @Override
    public void update(Rating entity) {

    }

    @Override
    public boolean deleteById(Long id) {


        return false;
    }

    /**
     * Возвращает сущность по идентификатору.
     *
     * @param id идентификатор
     * @return сущность
     */
    @Override
    public Rating getById(Long id) {
        String FIND_BY_ID_SQL = "SELECT id, data, value, name FROM ratings WHERE id=?;";
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
        String FIND_ALL_SQL = "SELECT id, data, value, name FROM ratings;";
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
                resultSet.getString("name")
        );
    }
}

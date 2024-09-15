package com.example.repository.impl;

import com.example.db.ConnectionManagerImpl;
import com.example.db.IConnectionManager;
import com.example.model.Subject;
import com.example.repository.SubjectRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubjectRepositoryImpl implements SubjectRepository {
    private static SubjectRepositoryImpl instance;
    private static final IConnectionManager connectionManager = ConnectionManagerImpl.getInstance();

    private SubjectRepositoryImpl() {
    }

    /**
     * Возвращает единственный экземпляр объекта класса {@code SubjectRepositoryImpl}.
     *
     * @return экземпляр класса
     */
    public static SubjectRepository getInstance() {
        if (instance == null) {
            instance = new SubjectRepositoryImpl();
        }

        return instance;
    }

    /**
     * Добавляет в базу данных сущность.
     *
     * @param entity сущность
     * @return сущность
     */
    @Override
    public Subject add(Subject entity) {
        return null;
    }

    /**
     * Обновляет в базе данных сущность.
     *
     * @param entity сущность
     */
    @Override
    public void update(Subject entity) {

    }

    /**
     * Удаляет из базы данных сущность по идентификатору.
     *
     * @param id идентификатор
     */
    @Override
    public boolean deleteById(Long id) {

        return false;
    }

    /**
     * Производит поиск сущности по заданному идентификатору.
     * Если сущность не найдена, то возвращает null.
     *
     * @param id идентификатор
     * @return сущность
     */
    @Override
    public Subject getById(Long id) {
        String FIND_BY_ID_SQL = "SELECT subject_id, subject_name FROM subjects WHERE subject_id = ?";
        Subject subject = null;
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                subject = createSubject(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return subject;
    }

    /**
     * Возвращает список всех сущностей.
     *
     * @return список сущностей
     */
    @Override
    public List<Subject> getAll() {
        String FIND_ALL_SQL = "SELECT subject_id, subject_name FROM subjects";
        List<Subject> subjectList = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                subjectList.add(createSubject(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return subjectList;
    }

    /**
     * Создает новую сущность по запросу.
     *
     * @param resultSet результат запроса
     * @return сущность
     * @throws SQLException если в запросе ошибка
     */
    private static Subject createSubject(ResultSet resultSet) throws SQLException {
        Long subjectId = resultSet.getLong("subject_id");

        return new Subject(subjectId, resultSet.getString("subject_name"));
    }

}

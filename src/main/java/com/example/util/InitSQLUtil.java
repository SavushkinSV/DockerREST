package com.example.util;

import com.example.db.IConnectionManager;
import com.example.exception.RepositoryException;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Класс {@code InitSQLUtil} инициализирует базу данных начальными значениями.
 */
public class InitSQLUtil {
    private static final String SCHEME = "sql/schema.sql";
    private static final String DATA = "sql/data.sql";
    private static String schemeSQL;
    private static String dataSQL;

    static {
        loadInitSQL();
    }

    private InitSQLUtil() {
    }

    /**
     * Инициализирует схему базы данных.
     *
     * @param connectionManager соединение с базой данных
     */
    public static void initSQLScheme(IConnectionManager connectionManager) {
        try (Connection connection = connectionManager.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(schemeSQL);
            System.out.println("Схема базы данных инициализирована");
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    /**
     * Заполняет базу данных начальными значиниями.
     *
     * @param connectionManager соединение с базой данных
     */
    public static void initSQLData(IConnectionManager connectionManager) {
        try (Connection connection = connectionManager.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(dataSQL);
            System.out.println("Данные добавлены в базу данных");
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }



    private static void loadInitSQL() {
        try (InputStream stream = InitSQLUtil.class.getClassLoader().getResourceAsStream(SCHEME)) {
            schemeSQL = new String(stream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new IllegalStateException();
        }
        try (InputStream inFile = InitSQLUtil.class.getClassLoader().getResourceAsStream(DATA)) {
            dataSQL = new String(inFile.readAllBytes(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new IllegalStateException();
        }
    }
}

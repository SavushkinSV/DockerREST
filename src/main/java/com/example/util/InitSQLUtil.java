package com.example.util;

import com.example.db.IConnectionManager;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Класс {@code InitSQLUtil} инициализирует базу данных начальными значениями.
 */
public class InitSQLUtil {
    private static final String INIT_PATH = "db-migration.sql";
    private static String schemeSQL;

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
            throw new RuntimeException(e);
        }
    }

    private static void loadInitSQL() {
        try (InputStream stream = InitSQLUtil.class.getClassLoader().getResourceAsStream(INIT_PATH)) {
            schemeSQL = new String(stream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new IllegalStateException();
        }

    }
}

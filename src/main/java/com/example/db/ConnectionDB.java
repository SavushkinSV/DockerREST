package com.example.db;

import com.example.utils.PropertiesUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Устанавливает соединение с базой данных.
 */
public class ConnectionDB {
    private static final String DB_DRIVER_CLASS_NAME = PropertiesUtil.getProperty("db.driver-class-name");
    private static final String DB_URL = PropertiesUtil.getProperty("db.url");
    private static final String DB_USER = PropertiesUtil.getProperty("db.user");
    private static final String DB_PASSWORD = PropertiesUtil.getProperty("db.password");

    /**
     * Устанавливает соединение с базой данных.
     *
     * @return соединение с базой данных
     * @throws ClassNotFoundException если класс не найден
     * @throws SQLException           если произошла ошибка доступа к базе данных или URL-адрес имеет значение NULL
     */
    public Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(DB_DRIVER_CLASS_NAME);
            System.out.println("Драйвер подключен");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Соединение с базой данных установлено");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return connection;
    }
}

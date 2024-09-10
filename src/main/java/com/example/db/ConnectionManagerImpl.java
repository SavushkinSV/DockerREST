package com.example.db;

import com.example.util.PropertiesUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Устанавливает соединение с базой данных.
 */
public class ConnectionManagerImpl implements IConnectionManager {
    private static ConnectionManagerImpl instance;
    private static final String DB_DRIVER_CLASS_NAME_KEY = "db.driver-class-name";
    private static final String DB_PORT_KEY = "db.local_port";
    private static final String DB_NAME_KEY = "db.name";
    private static final String DB_URL_KEY = "db.url";
    private static final String DB_URL = PropertiesUtil.getProperty(DB_URL_KEY) +
            PropertiesUtil.getProperty(DB_PORT_KEY) + "/" + PropertiesUtil.getProperty(DB_NAME_KEY);
    private static final String DB_USER_KEY = "db.user";
    private static final String DB_PASSWORD_KEY = "db.password";


    private ConnectionManagerImpl() {
    }

    /**
     * Возвращает объект класса {@code ConnectionDB}.
     *
     * @return объект класса
     */
    public static IConnectionManager getInstance() {
        if (instance == null) {
            instance = new ConnectionManagerImpl();
            loadDriver(PropertiesUtil.getProperty(DB_DRIVER_CLASS_NAME_KEY));
        }

        return instance;
    }

    /**
     * Загружает драйвер класса.
     *
     * @param driverClass имя драйвера класса
     * @throws ClassNotFoundException если драйвер класса не найден
     */
    private static void loadDriver(String driverClass) {
        try {
            Class.forName(driverClass);
            System.out.println("Database driver loaded.");
        } catch (ClassNotFoundException e) {
            System.out.println("Database driver not loaded.");
            throw new RuntimeException(e);
        }
    }

    /**
     * Устанавливает соединение с базой данных.
     *
     * @return соединение с базой данных
     * @throws ClassNotFoundException если класс не найден
     * @throws SQLException           если произошла ошибка доступа к базе данных или URL-адрес имеет значение NULL
     */
    public Connection getConnection() throws SQLException {

        return DriverManager.getConnection(
                DB_URL,
                PropertiesUtil.getProperty(DB_USER_KEY),
                PropertiesUtil.getProperty(DB_PASSWORD_KEY)
        );
    }
}

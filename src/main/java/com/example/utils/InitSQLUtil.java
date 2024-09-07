package com.example.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Класс {@code InitSQLUtil} инициализирует базу данных начальными значениями.
 */
public class InitSQLUtil {
    private static final String initSQL;

    private InitSQLUtil() {
    }

    static {
        try (InputStream stream = InitSQLUtil.class.getClassLoader().getResourceAsStream("sql/init.sql")) {
            initSQL = new String(stream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void initSQL(Connection connection) {
        try (Statement statement = connection.createStatement()) {
            statement.execute(initSQL);
            System.out.println("База данных инициализирована");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

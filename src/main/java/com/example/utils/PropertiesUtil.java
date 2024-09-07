package com.example.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Класс {@code PropertiesUtil} предоставляет метод для чтения свойств из файла по заданному ключу.
 *
 * @see <a href="https://javarush.com/quests/lectures/questcollections.level01.lecture09">...</a>
 */
public class PropertiesUtil {
    private static final Properties PROPERTIES = new Properties();

    static {
        try {
            InputStream stream = PropertiesUtil.class.getClassLoader().getResourceAsStream("db.properties");
            PROPERTIES.load(stream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Возвращает значение свойства по ключу из файла
     *
     * @param key ключ свойства
     * @return значение свойства
     */
    public static String getProperty(String key) {
        return PROPERTIES.getProperty(key);
    }
}

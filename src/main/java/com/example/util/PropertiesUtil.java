package com.example.util;

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
    private static final String PROPERTIES_FILE = "db.properties";

    static {
        loadProperties();
    }

    private PropertiesUtil() {
    }

    /**
     * Считывает список свойств (пары ключей и элементов) из файла.
     */
    private static void loadProperties() {
        try {
            InputStream stream = PropertiesUtil.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE);
            PROPERTIES.load(stream);
        } catch (Exception e) {
            throw new IllegalStateException();
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

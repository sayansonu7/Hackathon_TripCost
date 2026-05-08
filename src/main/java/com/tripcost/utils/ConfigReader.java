package com.tripcost.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static final Properties prop = new Properties();

    static {
        try (FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "/src/main/resources/config.properties")) {
            prop.load(fis);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String get(String key) {
        return prop.getProperty(key);
    }
}
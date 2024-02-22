package fmb.tools;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class DBConfig {

    private static final String CONFIG_FILE_PATH = "src/main/java/resources/productData.properties";

    private static Properties properties;

    static {
        properties = new Properties();
        try {
            FileInputStream input = new FileInputStream(CONFIG_FILE_PATH);
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getDatabaseUrl() {
        return properties.getProperty("DB_URL");
    }

    public static String getDBUsername() {
        return properties.getProperty("DB_USERNAME");
    }

    public static String getDBPassword() {
        return properties.getProperty("DB_PASSWORD");
    }

    public static void setProperties(String url, String username, String password) {
        properties.setProperty("DB_URL", url);
        properties.setProperty("DB_USERNAME", username);
        properties.setProperty("DB_PASSWORD", password);

        try {
            // Write properties to the properties file
            try (OutputStream output = new FileOutputStream(CONFIG_FILE_PATH)) {
                properties.store(output, "Database Properties");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void clearProperties() {
        properties.remove("DB_URL");
        properties.remove("DB_USERNAME");
        properties.remove("DB_PASSWORD");

        try {
            // Write properties to the properties file
            try (OutputStream output = new FileOutputStream(CONFIG_FILE_PATH)) {
                properties.store(output, "Database Properties");
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception (e.g., show an error message)
        }
    }

}

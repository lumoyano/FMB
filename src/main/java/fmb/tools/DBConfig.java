package fmb.tools;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DBConfig {

    private static final String CONFIG_FILE_PATH = "../../resources/productData.properties";

    private static Properties properties;

    static {
        properties = new Properties();
        try (FileInputStream input = new FileInputStream(CONFIG_FILE_PATH)) {
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
}

package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigProperties {
    public static Properties property;
    private static String configPath = "src/test/resources/config.properties";

    public static void initializePropertyFile(){
        property = new Properties();
        try {
            InputStream inputStream = new FileInputStream(configPath);
            property.load(inputStream);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

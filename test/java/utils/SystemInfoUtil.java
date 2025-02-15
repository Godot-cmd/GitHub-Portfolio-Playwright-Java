package utils;

import base.BaseClass;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class SystemInfoUtil extends BaseClass {

    public static void writeSystemInfo() {
        Properties properties = new Properties();
        properties.setProperty("os.name", System.getProperty("os.name"));
        properties.setProperty("os.version", System.getProperty("os.version"));
        properties.getProperty("os.arch");

        try (OutputStream output = new FileOutputStream("src/test/resources/environment.properties")) {
            properties.store(output, "Environment Information");
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    public static String getOperatingSystemName() {
        // Retrieve the OS name
        String osName = System.getProperty("os.name");

        // Return the OS name
        return osName;
    }

}

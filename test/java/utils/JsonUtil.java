package utils;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;

public class JsonUtil {

    private static final String DIRECTORY_PATH = "allure-results";
    private static final String FILE_NAME = "executor.json";

    private static JSONObject loadConfig() throws IOException {
        ConfigProperties.initializePropertyFile();
        try (FileInputStream fis = new FileInputStream(ConfigProperties.property.getProperty("ExecutorJson"))) {
            return new JSONObject(new JSONTokener(fis));
        }
    }

    public static void createExecutorJson(String suiteName) {
        // Create the directory if it doesn't exist
        File directory = new File(DIRECTORY_PATH);
        if (!directory.exists() && directory.mkdirs()) {
            System.out.println("Directory created: " + DIRECTORY_PATH);
        } else if (!directory.exists()) {
            System.err.println("Failed to create directory: " + DIRECTORY_PATH);
        }

        // Create JSON object
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", System.getProperty("user.name").toUpperCase());

        try {
            JSONObject config = loadConfig();
            if (!config.has(suiteName)) { // Check if suite name exists in the configuration
                throw new IllegalArgumentException("Unknown suite name: " + suiteName);
            }

            JSONObject suiteConfig = config.getJSONObject(suiteName);
            jsonObject.put("type", suiteConfig.getString("type"));
            jsonObject.put("buildName", suiteConfig.getString("buildName"));
            jsonObject.put("buildUrl", suiteConfig.getString("buildUrl"));
            jsonObject.put("reportName", suiteConfig.getString("reportName"));

        } catch (IOException e) {
            System.err.println("Error loading suite configuration: " + e.getMessage());
        }

        // Write JSON object to file
        File file = new File(directory, FILE_NAME);
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(jsonObject.toString(4)); // Pretty print with an indentation of 4 spaces
            System.out.println("JSON file created: " + file.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error writing JSON file: " + e.getMessage());
        }
    }
}


package utils;

import io.qameta.allure.Allure;
import io.qameta.allure.AllureLifecycle;
import org.testng.Assert;

import java.util.Map;

public class AllureUtils {
    public static void updateAllureReport(String testName, String description) {
        AllureLifecycle allureLifecycle = Allure.getLifecycle();
        allureLifecycle.updateTestCase(testResult -> {
            testResult.setName(testName);
            testResult.setDescription(description);
        });
    }

    public static void addAttachment(String name, String content) {
        Allure.addAttachment(name, content);
    }
    
}

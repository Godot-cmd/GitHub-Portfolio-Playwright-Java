package pom.pws;

import io.appium.java_client.windows.WindowsDriver;
import io.appium.java_client.windows.WindowsElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PWS {
    private WindowsDriver<WindowsElement> driver;
    private WebDriverWait wait;

    public PWS(WindowsDriver<WindowsElement> driver) {
        this.driver = driver;

    }
    private String serverStatus = "pws3notifiche_status";

    public void waitForFEUtoload() {
        wait = new WebDriverWait(driver, 200);

        wait.until(server -> {
            String status = driver.findElementByAccessibilityId(serverStatus).getAttribute("HelpText");
            return status.contains("connected") || status.contains("offline");
        });

    }
}

package pom.pws;

import io.appium.java_client.windows.WindowsDriver;
import io.appium.java_client.windows.WindowsElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

public class SchedaCliente {

    private WindowsDriver<WindowsElement> driver;

    public SchedaCliente(WindowsDriver<WindowsElement> driver) {
        this.driver = driver;
    }

    public void clickIndietroDaSC() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, 30);
            WebElement closeSession = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("< Indietro")));
            wait.until(ExpectedConditions.elementToBeClickable(By.name("< Indietro")));
            closeSession.click();
        } catch (Exception e) {
            System.out.println("Element not found or not clickable: " + e.getMessage());
        }
    }

}

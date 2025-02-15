package pom.logIn;

import base.BaseClass;
import com.google.common.collect.ImmutableMap;
import io.appium.java_client.windows.WindowsDriver;
import io.appium.java_client.windows.WindowsElement;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.*;

import java.util.concurrent.TimeUnit;

import static com.github.automatedowl.tools.AllureEnvironmentWriter.allureEnvironmentWriter;

public class IstFillWindow {
    private WindowsDriver<WindowsElement> driver;
    private WebDriverWait wait;

    private WindowsElement ist;
    private WindowsElement fil;
    private WindowsElement avvia;
    private WindowsElement bank;

    public IstFillWindow(WindowsDriver<WindowsElement> driver) {
        this.driver = driver;

        ist = driver.findElementByAccessibilityId("cmbBanca");
        fil = driver.findElementByAccessibilityId("cmbFiliale");
        bank = driver.findElementByName("Banca");
        avvia = driver.findElementByName("Avvia");

    }

    public void chooseBankAndFill(String banca, String filiale) {

        ist.sendKeys(banca);
        fil.sendKeys(filiale);

        XmlUtil.createEnvironmentXml(ist.getText(), fil.getText());

        bank.click();
        avvia.click();

        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        try {
            waitForWindowAndSwitch();
            wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByAccessibilityId("btnClose")));
            driver.findElementByAccessibilityId("btnClose").click();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    private void waitForWindowAndSwitch() {
        int retryCount = 3; // Number of retries
        int attempts = 0;
        boolean success = false;

        while (attempts < retryCount && !success) {
            try {
                wait = new WebDriverWait(driver, 10);
                wait.until(ExpectedConditions.numberOfWindowsToBe(1));

                for (String winHandle : driver.getWindowHandles()) {
                    System.out.println(winHandle + " errPage");
                    driver.switchTo().window(winHandle);
                    System.out.println(driver.getTitle() + " errPage");
                }
                success = true; // If no exception, mark success as true
            } catch (TimeoutException e) {
                attempts++;
                System.out.println("Timeout waiting for the window to appear. Attempt: " + attempts + " of " + retryCount);
                if (attempts >= retryCount) {
                    System.out.println("Max retries reached. Could not find the window: " + e.getMessage());
                }
            } catch (NoSuchWindowException e) {
                System.out.println("Window handle not found: " + e.getMessage());
                break; // Break out of loop as further attempts won't resolve this
            } catch (WebDriverException e) {
                System.out.println("WebDriver error: " + e.getMessage());
                break; // Break out of loop as further attempts won't resolve this
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
                break; // Break out of loop for any unexpected exceptions
            }
        }
    }
}

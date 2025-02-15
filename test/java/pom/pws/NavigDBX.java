package pom.pws;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import io.appium.java_client.windows.WindowsDriver;
import io.appium.java_client.windows.WindowsElement;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NavigDBX {
    private WindowsDriver<WindowsElement> driver;
    private WebDriverWait wait;
    private WindowsElement infoCli;

    public NavigDBX(WindowsDriver<WindowsElement> driver) {
        this.driver = driver;
    }
    public void navToAnagrafe() throws InterruptedException {

        wait = new WebDriverWait(driver, 120);

        infoCli = driver.findElementByAccessibilityId("menu_item_id_FITT0027");
        synchronized (infoCli) {
            infoCli.click();
        }

        synchronized (driver.findElementByName("DBX Anagrafe")) {
            driver.findElementByName("DBX Anagrafe").click();
            Thread.sleep(40000);
        }
        Thread.sleep(40000);
    }

}

package pom.anagrafe;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.WaitForSelectorState;
import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ConfigProperties;

import java.net.MalformedURLException;
import java.net.URL;

public class AnagrafeSchedeRecente {

    private FrameLocator frame;
    protected static WindowsDriver driver;

    protected static BrowserContext context;

    public AnagrafeSchedeRecente(Page page) {
//        this.page = page;
        this.frame = page.frameLocator(".dbx-quadrotto-router-iframe");
    }

    /*public boolean verificaSchedeVisualizzate(String desiredNdg) throws InterruptedException
    {
        String codiceFiscaAndNDGleList = "//dbxan-result-card//div[@class='card-container']//div[@class='name-details'][2]";
        Locator codiceFiscNDG = page.locator(codiceFiscaAndNDGleList);
        codiceFiscNDG.last().waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.ATTACHED).setTimeout(7000));

        boolean found = false;
        if (page.locator(codiceFiscaAndNDGleList, new Page.LocatorOptions().setHasText(desiredNdg)).isVisible()) {
            found = true;
            page.locator(codiceFiscaAndNDGleList, new Page.LocatorOptions().setHasText(desiredNdg)).click();
        }

        if (!found) {
            return false;
        }

        WindowsDriver driver = initializeDriver();

        if (driver == null) {
            throw new RuntimeException("Driver initialization failed.");
        }

        //clickIndietroDaSC();
        try {
            WebDriverWait wait = new WebDriverWait(driver, 30);
            WebElement closeSession = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("< Indietro")));
            closeSession.click();
        } catch (Exception e) {
            System.out.println("Element not found or not clickable: " + e.getMessage());
        }

        // Check if the element is not visible and return true if it is not present
        int timeout = 10000; // 10 seconds
        int interval = 500; // 0.5 seconds
        boolean isVisible = false;
        long startTime = System.currentTimeMillis();

        while (System.currentTimeMillis() - startTime < timeout) {
            page.locator(".card-container > .data-container").last().waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.ATTACHED).setTimeout(10000));
            isVisible = page.locator(".card-container > .data-container", new Page.LocatorOptions().setHasText(desiredNdg)).last().isVisible();
            if (isVisible) {
                break;
            }
            Thread.sleep(interval);
        }
        return isVisible;
    }*/

    public boolean verificaSchedeVisualizzate(String desiredNdg) throws InterruptedException
    {
        // Check if the element is not visible and return true if it is not present
        int timeout = 10000; // 10 seconds
        int interval = 500; // 0.5 seconds
        boolean isVisible = false;
        long startTime = System.currentTimeMillis();

        while (System.currentTimeMillis() - startTime < timeout) {
            frame.locator(".card-container > .data-container").last().waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.ATTACHED).setTimeout(10000));
            isVisible = frame.locator(".card-container > .data-container", new FrameLocator.LocatorOptions().setHasText(desiredNdg)).last().isVisible();
            if (isVisible) {
                break;
            }
            Thread.sleep(interval);
        }
        return isVisible;
    }

    public boolean verificaSchedeVisualizzateDropDown(String desiredNdg) throws InterruptedException
    {
        String codiceFiscaAndNDGDropDown = "//div[@role='option']//div[@class='details']//span[1]";
        Locator codiceFiscNDG = frame.locator(codiceFiscaAndNDGDropDown);
        codiceFiscNDG.last().waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.ATTACHED).setTimeout(20000));

        boolean found = false;
        if (frame.locator(codiceFiscaAndNDGDropDown, new FrameLocator.LocatorOptions().setHasText(desiredNdg)).last().isVisible()) {
            found = true;
            frame.locator(codiceFiscaAndNDGDropDown, new FrameLocator.LocatorOptions().setHasText(desiredNdg)).last().click();
        }

        if (!found) {
            return false;
        }

        WindowsDriver driver = initializeDriver();

        if (driver == null) {
            throw new RuntimeException("Driver initialization failed.");
        }

        //clickIndietroDaSC();
        try {
            WebDriverWait wait = new WebDriverWait(driver, 30);
            WebElement closeSession = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("< Indietro")));
            wait.until(ExpectedConditions.elementToBeClickable(By.name("< Indietro")));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("< Indietro")));
            closeSession.click();
        } catch (Exception e) {
            System.out.println("Element not found or not clickable: " + e.getMessage());
        }

        // Check if the element is not visible and return true if it is not present
        int timeout = 30000; // 10 seconds
        int interval = 500; // 0.5 seconds
        boolean isVisible = false;
        long startTime = System.currentTimeMillis();

        while (System.currentTimeMillis() - startTime < timeout) {
            frame.locator(".card-container > .data-container").last().waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.ATTACHED).setTimeout(20000));
            isVisible = frame.locator(".card-container > .data-container", new FrameLocator.LocatorOptions().setHasText(desiredNdg)).last().isVisible();
            if (isVisible) {
                break;
            }
            Thread.sleep(interval);
        }
        return isVisible;
    }

    private WindowsDriver initializeDriver() {
        try {
            // Create DesiredCapabilities object
            DesiredCapabilities desktopCapabilities = new DesiredCapabilities();
            desktopCapabilities.setCapability("app", "Root");
            desktopCapabilities.setCapability("platformName", "Windows");
            desktopCapabilities.setCapability("deviceName", "WindowsPC");

            // Initialize the WindowsDriver with the desired capabilities and URL from properties
            WindowsDriver driver = new WindowsDriver(new URL(ConfigProperties.property.getProperty("WinAppDriverURLport")), desktopCapabilities);
            return driver;
        } catch (MalformedURLException e) {
            System.err.println("MalformedURLException: " + e.getMessage());
            return null;
        }
    }

    public void clickIndietroDaSC() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, 30);
            WebElement closeSession = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("< Indietro")));
            closeSession.click();
        } catch (Exception e) {
            System.out.println("Element not found or not clickable: " + e.getMessage());
        }
    }
}

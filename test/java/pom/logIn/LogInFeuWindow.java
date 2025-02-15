package pom.logIn;

import io.appium.java_client.windows.WindowsDriver;
import io.appium.java_client.windows.WindowsElement;

public class LogInFeuWindow {
    private WindowsDriver<WindowsElement> driver;

    private WindowsElement utente;
    private WindowsElement passwordField;
    private WindowsElement logIn;

    public LogInFeuWindow(WindowsDriver<WindowsElement> driver) {
        this.driver = driver;

        utente = driver.findElementByAccessibilityId("Utente");
        passwordField = driver.findElementByAccessibilityId("Password");
        logIn = driver.findElementByName("Log in");

    }
    public void login(String username, String password){

        utente.clear();
        utente.sendKeys(username);
        passwordField.sendKeys(password);
        logIn.click();

    }
}

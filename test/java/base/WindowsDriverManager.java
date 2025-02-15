package base;

import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import pom.logIn.IstFillWindow;
import pom.logIn.LogInFeuWindow;
import pom.pws.NavigDBX;
import pom.pws.PWS;
import utils.ConfigProperties;
import utils.ExcelUtil;
import utils.Utils;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class WindowsDriverManager {

    private static Path _dataDir;
    protected static WindowsDriver driver;
    private WebElement MAWebElement;

    public void startWinAppDriver() throws IOException {
        if (isProcessRunning("WinAppDriver.exe")) {
            System.out.println("WinAppDriver is already running.");
        } else {
            Desktop desktop = Desktop.getDesktop();
            desktop.open(new File(ConfigProperties.property.getProperty("WinAppDriverDir")));
            System.out.println("Starting WinAppDriver...");
        }
    }

    public boolean isProcessRunning(String processName) throws IOException {
        String line;
        Process p = Runtime.getRuntime().exec("tasklist");
        BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));

        while ((line = input.readLine()) != null) {
            if (line.contains(processName)) {
                input.close();
                return true;
            }
        }
        input.close();
        return false;
    }

    public void startApp() throws InterruptedException {
        ConfigProperties.initializePropertyFile();
        try {
            _dataDir = Files.createTempDirectory("pw-java-webview2-tests-");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Start WinAppDriver using ProcessBuilder
        ProcessBuilder processBuilder = new ProcessBuilder(ConfigProperties.property.getProperty("App"));
        processBuilder.directory(new File(ConfigProperties.property.getProperty("AppDir")));
        Map<String, String> envMap = processBuilder.environment();
        envMap.put("WEBVIEW2_ADDITIONAL_BROWSER_ARGUMENTS", ConfigProperties.property.getProperty("RemoteDebuggingPort") + " --auto-open-devtools-for-tabs");
        //envMap.put("WEBVIEW2_USER_DATA_FOLDER", _dataDir.toString());
        try {
            Process process = processBuilder.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Thread.sleep(10000);
    }

    public void attachToApp() throws MalformedURLException {
        DesiredCapabilities desktopCapabilities = new DesiredCapabilities();
        desktopCapabilities.setCapability("app", "Root");
        desktopCapabilities.setCapability("platformName", "Windows");
        desktopCapabilities.setCapability("deviceName", "WindowsPC");

        // You get the desktop session
        WindowsDriver DesktopSession = new WindowsDriver(new URL(ConfigProperties.property.getProperty("WinAppDriverURLport")), desktopCapabilities);

        // Here you find the already running application and get the handle

        if (ConfigProperties.property.getProperty("App").contains("Sviluppo")) {
            MAWebElement = DesktopSession.findElementByName("Login: Sviluppo");
        } else if (ConfigProperties.property.getProperty("App").contains("Collaudo")) {
            MAWebElement = DesktopSession.findElementByName("Login: Collaudo");
        } else if (ConfigProperties.property.getProperty("App").contains("Addestramento")) {
            MAWebElement = DesktopSession.findElementByName("Login: Addestramento");
        }

        String MAWinHandleStr = MAWebElement.getAttribute("NativeWindowHandle");
        int MAWinHandleInt = Integer.parseInt(MAWinHandleStr);
        String MAWinHandleHex = Integer.toHexString(MAWinHandleInt);

        // You attach to the already running application
        DesiredCapabilities MACapabilities = new DesiredCapabilities();
        MACapabilities.setCapability("platformName", "Windows");
        MACapabilities.setCapability("deviceName", "WindowsPC");
        // You set the Handle as one of the capabilities
        MACapabilities.setCapability("appTopLevelWindow", MAWinHandleHex);

        // My Application Session
        driver = new WindowsDriver(new URL(ConfigProperties.property.getProperty("WinAppDriverURLport")), MACapabilities);
    }

    public void navToWebviewPage() throws InterruptedException {
        LogInFeuWindow loginPage = new LogInFeuWindow(driver);
        loginPage.login(ExcelUtil.GetData("Global", "User"), ExcelUtil.GetData("Global", "Password"));
        Utils.waitForWindowAndSwitch(driver, 1, 200);

        IstFillWindow istFillPagePom = new IstFillWindow(driver);
        istFillPagePom.chooseBankAndFill(ExcelUtil.GetData("Global", "Ist"), ExcelUtil.GetData("Global", "Fil"));

        Utils.waitForWindowAndSwitch(driver, 1, 240);

        PWS pws = new PWS(driver);
        pws.waitForFEUtoload();

        NavigDBX mainPagePom = new NavigDBX(driver);
        mainPagePom.navToAnagrafe();

        Thread.sleep(20000);
    }
    public void closeApp(){
        driver.close();
        Utils.waitForWindowAndSwitch(driver,1,10);
        driver.findElementByAccessibilityId("1").click();

    }

    public void closeWinAppDriver() throws IOException {
        Runtime.getRuntime().exec("taskkill /F /IM WinAppDriver.exe");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}

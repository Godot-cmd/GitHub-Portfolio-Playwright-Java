package base;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import utils.*;

import java.io.IOException;

public class BaseClass extends PlaywrightManager {

    private static PlaywrightManager playwrightManager;
    private static WindowsDriverManager windowsDriverManager;

    @BeforeSuite
    public void setUp() throws InterruptedException, IOException {

        ConfigProperties.initializePropertyFile();
        windowsDriverManager = new WindowsDriverManager();
        windowsDriverManager.startWinAppDriver();
        windowsDriverManager.startApp();
        windowsDriverManager.attachToApp();
        windowsDriverManager.navToWebviewPage();
        playwrightManager = new PlaywrightManager();
        playwrightManager.initialiezePlaywright();

    }

    @AfterSuite
    public void exitApp() {
        windowsDriverManager.closeApp();

    }

}



package base;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import pom.pws.NavigDBX;
import utils.ConfigProperties;

public class PlaywrightManager extends WindowsDriverManager {

    protected static Playwright playwright;
    protected static Browser browser;
    protected static BrowserContext context;
    protected static Page page;

    public void initialiezePlaywright() {
        ConfigProperties.initializePropertyFile();
        playwright = Playwright.create();
        browser = playwright.chromium().connectOverCDP(ConfigProperties.property.getProperty("EndPointUrl"));
        context = browser.contexts().get(0);
        page = context.pages().get(0);
        page.setDefaultTimeout(200000);
    }
}

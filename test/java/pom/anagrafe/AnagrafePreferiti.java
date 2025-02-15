package pom.anagrafe;

import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import java.lang.Thread;
import com.microsoft.playwright.options.AriaRole;
public class AnagrafePreferiti {

    private FrameLocator frame;

    public AnagrafePreferiti(Page page) {
//        this.page = page;
        this.frame = page.frameLocator(".dbx-quadrotto-router-iframe");
    }


    public boolean addRemoveFavorites(String desiredNdg) throws InterruptedException
    {

        String codiceFiscaAndNDGleList = "//dbxan-result-card//div[@class='card-container']//div[@class='tax-details']";
        Locator codiceFiscNDG = frame.locator(codiceFiscaAndNDGleList);
        codiceFiscNDG.last().waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.ATTACHED).setTimeout(14000));    //7000

        boolean found = false;
        int count = codiceFiscNDG.count();
        for (int i = 0; i < count; i++) {
            String textContent = codiceFiscNDG.nth(i).textContent()
                    .replace(" ", "")
                    .replace("(", "")
                    .replace(")", "");
            String[] splitContent = textContent.split("-");
            for (String text : splitContent) {
                if (text.contains(desiredNdg)) {
                    codiceFiscNDG.nth(i).hover();
                    found = true;
                    break;
                }
            }
            if (found) break;
        }

        if (!found) {
            return false;
        }

        Locator butonEdit = frame.locator("//div[@class='button-edit']").first();
        butonEdit.click();

        Locator buttonStar = frame.getByLabel("favorite").last();
        buttonStar.click();

        try {
            frame.getByText("arrow_left").click();
        } catch (Exception e) {
            e.printStackTrace();
        }

        frame.locator("//dbxan-favorite-client-card[1]", new FrameLocator.LocatorOptions().setHasText(desiredNdg)).hover();
        Locator btnEdt = frame.locator("//dbxan-favorite-client-card[1]//div[1]//ion-button[1]");
        btnEdt.click();

        Locator butnStar = frame.getByLabel("favorite_filled").last();
        butnStar.click();

        int timeout = 10000; // 10 seconds
        int interval = 500; // 0.5 seconds
        boolean isNotVisible = false;
        long startTime = System.currentTimeMillis();

        while (System.currentTimeMillis() - startTime < timeout) {
            isNotVisible = !frame.locator("//dbxan-favorite-client-card[1]", new FrameLocator.LocatorOptions().setHasText(desiredNdg)).isVisible();
            if (isNotVisible) {
                break;
            }
            Thread.sleep(interval);
        }
        return isNotVisible;
    }

    public boolean addRemoveFavoritesMainPage(String desiredNdg) throws InterruptedException
    {
//        frame.setDefaultTimeout(12000);

        String codiceFiscaAndNDGleList = "//dbxan-searchbar-option-light//div[@class='details']";
        Locator codiceFiscNDG = frame.locator(codiceFiscaAndNDGleList);
        codiceFiscNDG.last().waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.ATTACHED).setTimeout(14000));    //7000

        boolean found = false;
        int count = codiceFiscNDG.count();
        for (int i = 0; i < count; i++) {
            String textContent = codiceFiscNDG.nth(i).textContent()
                    .replace(" ", "")
                    .replace("(", "")
                    .replace(")", "");
            String[] splitContent = textContent.split("-");
            for (String text : splitContent) {
                if (text.contains(desiredNdg)) {
                    codiceFiscNDG.nth(i).click();
                    found = true;
                    break;
                }
            }
            if (found) break;
        }

        if (!found) {
            return false;
        }

        frame.getByRole(AriaRole.BUTTON, new FrameLocator.GetByRoleOptions().setName("Aggiungi")).click();

        frame.locator("//dbxan-favorite-client-card[1]", new FrameLocator.LocatorOptions().setHasText(desiredNdg)).hover();
        Locator btnEdt = frame.locator("//dbxan-favorite-client-card[1]//div[1]//ion-button[1]");
        btnEdt.click();

        Locator butnStar = frame.getByLabel("favorite_filled").last();;
        butnStar.click();

        // Check if the element is not visible and return true if it is not present
        int timeout = 10000; // 10 seconds
        int interval = 500; // 0.5 seconds
        boolean isNotVisible = false;
        long startTime = System.currentTimeMillis();

        while (System.currentTimeMillis() - startTime < timeout) {
            isNotVisible = !frame.locator("//dbxan-favorite-client-card[1]", new FrameLocator.LocatorOptions().setHasText(desiredNdg)).isVisible();
            if (isNotVisible) {
                break;
            }
            Thread.sleep(interval);
        }
        return isNotVisible;
    }

}

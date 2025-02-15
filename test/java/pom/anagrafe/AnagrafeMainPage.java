package pom.anagrafe;

import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.*;

public class AnagrafeMainPage {

    private FrameLocator frame;

    public AnagrafeMainPage(Page page) {
//        this.page = page;
        this.frame = page.frameLocator(".dbx-quadrotto-router-iframe");
    }

    private String searchField = "ion-autocomplete[class='search-bar abc-override'] input[class='autocomplete-input']";
    private String cercaBtn = "ion-button[class='ricerca-button abc-button-override'] button[type='button']";

    private String codFiscFromDropDown = "//div[@role='option']//div[@class='details']//span[3]";
    private String ndgFromDropDown = "//div[@role='option']//div[@class='details']//span[1]";
    private String dataDiNascitaDropdown = "//div[@role='option']//div[@class='filiale-container']//span[2]";
    private String fullNameDropDown = "//div[@role='option']//div[@class='data-container']//h4[@class='name']";
    private String noResult = "//dbxan-searchbar-header//b[text()='0 Risultati']";

    public void cerca(String searchValue) throws InterruptedException {
        try {
            frame.getByRole(AriaRole.COMBOBOX, new FrameLocator.GetByRoleOptions()).clear();
            frame.getByRole(AriaRole.COMBOBOX, new FrameLocator.GetByRoleOptions()).type(searchValue);
            frame.locator(ndgFromDropDown).last().waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.ATTACHED).setTimeout(20000));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void clickCercaBtn() throws InterruptedException {

        frame.locator(cercaBtn).first().click();
    }

    public void cercaNoResult(String searchValue) throws InterruptedException {
        try {
            frame.getByRole(AriaRole.COMBOBOX, new FrameLocator.GetByRoleOptions()).clear();
            frame.getByRole(AriaRole.COMBOBOX, new FrameLocator.GetByRoleOptions()).type(searchValue);
            frame.locator(noResult).last().waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.ATTACHED).setTimeout(12000));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean dbxIsVisible() {

        return frame.locator(searchField).isEditable();

    }

    public boolean isDropDownCodFiscVisible(String desiredDropdownCodFisc) {
        frame.locator(codFiscFromDropDown).last().waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.ATTACHED).setTimeout(12000));
        return frame.locator(codFiscFromDropDown, new FrameLocator.LocatorOptions().setHasText(desiredDropdownCodFisc)).last().isVisible();
    }

    public boolean isDropDownNdgVisible(String desiredDropDownNdg) {
        return frame.locator(ndgFromDropDown, new FrameLocator.LocatorOptions().setHasText(desiredDropDownNdg)).last().isVisible();
    }

    public boolean isFullNameFromDropdownVisible(String desiredFullName) {
        frame.locator(ndgFromDropDown).last().waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.ATTACHED).setTimeout(20000));
        return frame.locator(fullNameDropDown, new FrameLocator.LocatorOptions().setHasText(desiredFullName)).nth(-1).isVisible();
    }

    public boolean isFullNameDataNascitaDropDownsVisible(String searchString) {


        Locator nameListElement = frame.locator(dataDiNascitaDropdown).first();
        Locator nameElement = frame.locator(fullNameDropDown).first();

        String nameListText = nameListElement.textContent().trim().toLowerCase();
        String actualText = nameElement.textContent().trim().toLowerCase();

        String[] words = searchString.trim().toLowerCase().split("\\s+");

        boolean isNameListTextContainAnyWord = false;
        for (String word : words) {
            if (nameListText.contains(word)) {
                isNameListTextContainAnyWord = true;
                break;
            }
        }

        boolean isActualTextContainAnyWord = false;
        for (String word : words) {
            if (actualText.contains(word)) {
                isActualTextContainAnyWord = true;
                break;
            }
        }

        return isNameListTextContainAnyWord && isActualTextContainAnyWord;
    }

    public boolean isDropDownCodFiscNotVisible(String desiredDropdownCodFisc) {
        return !frame.locator(codFiscFromDropDown, new FrameLocator.LocatorOptions().setHasText(desiredDropdownCodFisc)).isVisible();
    }

    public boolean isDropDownNdgNotVisible(String desiredDropDownNdg) {
        return !frame.locator(ndgFromDropDown, new FrameLocator.LocatorOptions().setHasText(desiredDropDownNdg)).isVisible();
    }

    public boolean isFullNameFromDropdownNotVisible(String desiredFullName) {
        return !frame.locator(fullNameDropDown, new FrameLocator.LocatorOptions().setHasText(desiredFullName)).isVisible();
    }

    public void cercaPreferitiMainPage(String searchValue) throws InterruptedException {
        try {
            frame.locator("//div[@class='aggiungi-name']").click();
            frame.getByRole(AriaRole.COMBOBOX, new FrameLocator.GetByRoleOptions().setName("Inserisci codice fiscale, nome e cognome, NDG")).type(searchValue);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}



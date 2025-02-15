package pom.anagrafe;

import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

public class AnagrafeCercaResultsPage {
    private Page page;
    private FrameLocator frame;

    public AnagrafeCercaResultsPage(Page page) {
        this.page = page;
        this.frame = page.frameLocator(".dbx-quadrotto-router-iframe");
    }

    private String nameList = "//div[@class='results-page-container']//dbxan-result-card//div[@class='name-details']";
    private String codiceFiscaAndNDGleList = "//dbxan-result-card//div[@class='card-container']//div[@class='tax-details']";
    private String dataDiNascitaList = "//div[@class='card-container']//div[@class='details'][2]//div[@class='contact-details'][2]";
    private String noResultSearch = "//div[@class='message-container']//h4";

    public void clickIndietro(){

        String indietroBtn = ".pane-content .search-bar-container ion-icon span:has-text('arrow_left')";

        try{
            frame.locator(indietroBtn).waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.ATTACHED));
            frame.locator(indietroBtn).click();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isNameVisible(String desiredName) {
        Locator nameElement = frame.locator(nameList).first();
        String actualText = nameElement.textContent().trim().replaceAll("\\s+", " ").toLowerCase();
        String normalizedDesiredName = desiredName.trim().replaceAll("\\s+", " ").toLowerCase();

        return actualText.contains(normalizedDesiredName);
    }

    public boolean isNameVisible3Caratteri(String desiredName) {
        Locator nameElement = frame.locator(nameList);

        int count = nameElement.count();
        System.out.println("Number of elements found: " + count);
        String normalizedFullName = desiredName.trim().toUpperCase();

        for (int i = 0; i < count; i++) {
            String textContent = nameElement.nth(i).textContent()
                    .replaceAll("[\\s()]", "")
                    .toUpperCase();
            if (!textContent.contains(normalizedFullName)) {
                return false;
            }
        }
        return true;
    }

    public boolean isNoResultVisible(String desiredName) {
        Locator nameElement = frame.locator(noResultSearch);
        String actualText = nameElement.textContent().trim().replaceAll("\\s+", " ");
        String normalizedDesiredName = desiredName.trim().replaceAll("\\s+", " ");

        return actualText.equals(normalizedDesiredName);
    }

    public boolean isCognomeVisible(String desiredCodFiscOrNdg) {
        Locator codiceFiscNDG = frame.locator(nameList);
        codiceFiscNDG.first().waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.ATTACHED).setTimeout(15000));   //10000    last()
        int count = codiceFiscNDG.count();
        System.out.println("Number of elements found: " + count);
        String normalizedDesiredCodFiscOrNdg = desiredCodFiscOrNdg.trim().toUpperCase();
        for (int i = 0; i < count; i++) {
            String textContent = codiceFiscNDG.nth(i).textContent()
                    .replaceAll("[\\s()]", "")
                    .toUpperCase();
            if (textContent.contains(normalizedDesiredCodFiscOrNdg)) {
                return true;
            }
        }
        return false;
    }

    public boolean isCognomeNomeDataVisible(String desiredName) {
        Locator nameListElement = frame.locator(dataDiNascitaList).first();
        Locator nameElement = frame.locator(nameList).first();

        String nameListText = nameListElement.textContent().trim().toLowerCase();
        String actualText = nameElement.textContent().trim().toLowerCase();

        String[] words = desiredName.trim().toLowerCase().split("\\s+");

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

    public boolean isCodiceFiscaleOrNdgVisible(String desiredCodFiscOrNdg) {

        Locator codiceFiscNDG = frame.locator(codiceFiscaAndNDGleList);
        codiceFiscNDG.last().waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.ATTACHED).setTimeout(15000));      //10000
        int count = codiceFiscNDG.count();
        System.out.println(count);
        for (int i = 0; i < count; i++) {
            String textContent = codiceFiscNDG.nth(i).textContent()
                    .replace(" ", "")
                    .replace("(","")
                    .replace(")","");
            String[] splitContent = textContent.split("-");
            for (String text : splitContent) {
                //if (text.equals(desiredCodFiscOrNdg))
                if (text.contains(desiredCodFiscOrNdg))
                    return true;
            }
        }
        return false;
    }

}
package tests;

import base.BaseClass;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.WaitForSelectorState;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.testng.Assert;
import org.testng.annotations.Test;
import pom.anagrafe.AnagrafeMainPage;

@Feature("Accedi a DBX Anagrafe")
@DisplayName("Accedi a DBX Anagrafe")
public class LoginDBX extends BaseClass {


    @Test(priority = 1, testName = "Login DBX")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Login DBX Anagrafe")
    @Story("Login DBX Anagrafe")
    public void logInDBX() {
        AllureLifecycle allureLifecycle = Allure.getLifecycle();
        allureLifecycle.updateTestCase(testResult -> {
            testResult.setName("Login DBX");});
        try {
            /*AnagrafeLogInPage anagrafeLogInPage = new AnagrafeLogInPage(page);
            anagrafeLogInPage.logInDBX("web", "webpwd");*/
            page.locator("//h3[text()='Gestione anagrafica']").last()
                    .waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.ATTACHED).setTimeout(10000));
            //Thread.sleep(10000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(priority = 2, testName = "Verifica se la pagina DBX viene visualizzata")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Verifica se la pagina DBX Anagrafe viene visualizzata")
    @Story("Verifica se la pagina DBX Anagrafe viene visualizzata")
    public void checkIfDbxIsVisible() {

        AllureLifecycle allureLifecycle = Allure.getLifecycle();
        allureLifecycle.updateTestCase(testResult -> {
            testResult.setName("Verifica se la pagina DBX viene visualizzata");});

        try {
            AnagrafeMainPage anagrafePom = new AnagrafeMainPage(page);
            Assert.assertTrue(anagrafePom.dbxIsVisible());
        } catch (AssertionError e){
            Allure.addAttachment("Verifica se la pagina DBX Anagrafe viene visualizzata", "DBX Anagrafe DBX non è visibile.");
            throw e;
        } catch (Exception e) {
            // Handle other unexpected exceptions
            Allure.addAttachment("Verifica se la pagina DBX Anagrafe viene visualizzata", "Si è verificato un errore imprevisto durante il verifica della visibilità DBX: \n" + e.getMessage());
            throw e; // Re-throw the exception to mark the test as failed
        }

    }

}

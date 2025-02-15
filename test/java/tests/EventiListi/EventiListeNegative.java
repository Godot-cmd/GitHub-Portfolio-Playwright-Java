package tests.EventiListi;

import base.BaseClass;
import dataproviders.DataProviders;
import io.qameta.allure.Allure;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.DisplayName;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pom.censimento.AnagrafeCensimentoProspect;
import utils.AllureUtils;
import utils.ConfigProperties;

import utils.ExcelUtil;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Feature("Censimento Prospect - Validazione Codice Fiscale")
@DisplayName("Censimento Prospect - Validazione Codice Fiscale")
public class EventiListeNegative extends BaseClass {

    private static String EXCEL_FILE_PATH;
    private static final String PROVIDER_NAME = "Dati PF";

    private static AnagrafeCensimentoProspect anagrafeCensimentoProspect;
    private static final AtomicInteger testIdCounter = new AtomicInteger(1);

    @BeforeClass
    public static void initialize() throws InterruptedException {
        anagrafeCensimentoProspect = new AnagrafeCensimentoProspect(page);
        ConfigProperties.initializePropertyFile();
        EXCEL_FILE_PATH = ConfigProperties.property.getProperty("DBX249");
    }

    private int getNextTestId() {
        return testIdCounter.getAndIncrement();
    }

    @Test(dataProvider = PROVIDER_NAME, dataProviderClass = DataProviders.class, priority = 1, testName = "Verifica Eventi")
    @Severity(SeverityLevel.CRITICAL)
    public void verificaEventi(String codiceFiscale, String cognome, String nome, String telefono, String email) throws InterruptedException {

        Map<String, String> testData = ExcelUtil.RetrieveTestData(EXCEL_FILE_PATH, getNextTestId());
        String testName = testData.get("TestName");
        String testDescription = testData.get("TestDescription");
        String expectedResults = testData.get("ExpectedResults");

        AllureUtils.updateAllureReport(testName, testDescription);

        anagrafeCensimentoProspect.clickCensimentoProspect();
        anagrafeCensimentoProspect.inserimentoPF(codiceFiscale, cognome, nome, telefono, email);
        anagrafeCensimentoProspect.clickAvviaVerifica();

        try {
            Assert.assertTrue(anagrafeCensimentoProspect.verificaPresenzaEventi());
        } catch (AssertionError e) {
            Allure.addAttachment("Verifica Eventi", "Il tooltip non è visibile");
            throw e;
        } catch (Exception e) {
            Allure.addAttachment("Verifica Eventi", "Si è verificato un errore imprevisto durante la verifica della visibilità DBX: \n" + e.getMessage());
            throw e; // Re-throw the exception to mark the test as failed
        } finally {
            // Optional cleanup if necessary
            // anagrafeCensimentoProspect.clickAnnulla();
        }
        Allure.addAttachment("Expected Results", expectedResults);
    }

    @Test(dataProvider = PROVIDER_NAME, dataProviderClass = DataProviders.class, priority = 2, testName = "Verifica Liste")
    @Severity(SeverityLevel.CRITICAL)
    public void verificaListe(String codiceFiscale, String cognome, String nome, String telefono, String email) throws InterruptedException {

        Map<String, String> testData = ExcelUtil.RetrieveTestData(EXCEL_FILE_PATH, getNextTestId());
        String testName = testData.get("TestName");
        String testDescription = testData.get("TestDescription");
        String expectedResults = testData.get("ExpectedResults");

        AllureUtils.updateAllureReport(testName, testDescription);

        anagrafeCensimentoProspect.clickCensimentoProspect();
        anagrafeCensimentoProspect.inserimentoPF(codiceFiscale, cognome, nome, telefono, email);
        anagrafeCensimentoProspect.clickAvviaVerifica();

        try {
            Assert.assertTrue(anagrafeCensimentoProspect.verificaPresenzaListe());
        } catch (AssertionError e) {
            Allure.addAttachment("Verifica Liste", "Il tooltip non è visibile");
            throw e;
        } catch (Exception e) {
            Allure.addAttachment("Verifica Liste", "Si è verificato un errore imprevisto durante la verifica della visibilità DBX: \n" + e.getMessage());
            throw e; // Re-throw the exception to mark the test as failed
        } finally {
            // Optional cleanup if necessary
            // anagrafeCensimentoProspect.clickAnnulla();
        }
        Allure.addAttachment("Expected Results", expectedResults);
    }

    @AfterMethod
    public void tearDown() {
        try {
            anagrafeCensimentoProspect.clickAnnulla();
        } catch (Exception e) {}
    }

    @AfterClass
    public void cleanVariables() {
        try {
            anagrafeCensimentoProspect = null;
        } catch (Exception e) {}
    }
}

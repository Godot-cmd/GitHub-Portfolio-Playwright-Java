package tests.ValidazioneProspect;

import base.BaseClass;
import dataproviders.DataProviders;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.testng.Assert;
import org.testng.annotations.*;
import pom.censimento.AnagrafeCensimentoProspect;
import utils.AllureUtils;
import utils.ConfigProperties;
import utils.ExcelUtil;
import utils.ExecuteUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Feature("Censimento Prospect PF Panel - Dati principali - Codice fiscale")
@DisplayName("Censimento Prospect PF Panel - Dati principali - Codice fiscale")
public class ValidazioneCodiceFiscale extends BaseClass {

    public final String provider_name = "Validazione CF";
    private static  String EXCEL_FILE_PATH;
    private static AnagrafeCensimentoProspect anagrafeCensimentoProspect;
    private static final Map<String, AtomicInteger> testIdMap = new HashMap<>();

    static {
        // Initialize the map with test method names and their starting testIds
        testIdMap.put("validazioneCodiceFiscale", new AtomicInteger(6));
        testIdMap.put("validazioneCodiceIncola", new AtomicInteger(11));
    }

    private int getNextTestId(String testMethodName) {
        AtomicInteger testIdCounter = testIdMap.get(testMethodName);
        if (testIdCounter != null) {
            return testIdCounter.getAndIncrement();
        } else {
            throw new IllegalArgumentException("Test method not found in map: " + testMethodName);
        }
    }

    @BeforeClass
    public static void initialize() throws InterruptedException {
        anagrafeCensimentoProspect = new AnagrafeCensimentoProspect(page);
        ConfigProperties.initializePropertyFile();
        EXCEL_FILE_PATH = ConfigProperties.property.getProperty("DBX249");
    }

    @Test(priority = 1, testName = "Verifica Presenza Codice Fiscale")
    @Severity(SeverityLevel.CRITICAL)
    public void verificaPresenzaCodiceFiscale() throws InterruptedException {
        anagrafeCensimentoProspect.clickCensimentoProspect();
        ExecuteUtils.executeTestTrueNoParam(EXCEL_FILE_PATH, 1, "Il campo Codice Fiscale", anagrafeCensimentoProspect.isTaxCodePresent());
    }

    @Test(dataProvider = provider_name, dataProviderClass = DataProviders.class, priority = 2, testName = "Validazione Codice Fiscale")
    @Severity(SeverityLevel.CRITICAL)
    public void validazioneCodiceFiscale(String codiceFiscale, String expectedTooltipMsg) throws InterruptedException {

        Map<String, String> testData = ExcelUtil.RetrieveTestData(EXCEL_FILE_PATH, getNextTestId("validazioneCodiceFiscale"));

        String testName = testData.get("TestName");
        String testDescription = testData.get("TestDescription");
        String expectedResults = testData.get("ExpectedResults");

        AllureUtils.updateAllureReport(testName, testDescription);

        anagrafeCensimentoProspect.clickCensimentoProspect();

        try {
            Assert.assertEquals(anagrafeCensimentoProspect.validazioneCodiceFiscale(codiceFiscale), expectedTooltipMsg);

        } catch (AssertionError e){
            Allure.addAttachment("Validazione Codice Fiscale", " Il tooltip " + expectedTooltipMsg + " non è visibile");
            throw e;
        } catch (Exception e) {
            Allure.addAttachment("Validazione Codice Fiscale", "Si è verificato un errore imprevisto durante il verifica della visibilità DBX: \n" + e.getMessage());
            throw e;
        }
        Allure.addAttachment("Expected Results", expectedResults);
    }

    @Test(dataProvider = provider_name, dataProviderClass = DataProviders.class, priority = 3, testName = "Validazione Codice Fiscale Incolla")
    @Severity(SeverityLevel.CRITICAL)
    public void validazioneCodiceIncola(String codiceFiscale, String expectedTooltipMsg) throws InterruptedException {

        Map<String, String> testData = ExcelUtil.RetrieveTestData(EXCEL_FILE_PATH, getNextTestId("validazioneCodiceIncola"));

        String testName = testData.get("TestName");
        String testDescription = testData.get("TestDescription");
        String expectedResults = testData.get("ExpectedResults");

        AllureUtils.updateAllureReport(testName, testDescription);

        anagrafeCensimentoProspect.clickCensimentoProspect();

        try {
            Assert.assertEquals(anagrafeCensimentoProspect.validazioneCodiceFiscaleIncolla(codiceFiscale), expectedTooltipMsg);

        } catch (AssertionError e) {
            Allure.addAttachment("Validazione Codice Fiscale Incolla", " Il tooltip " + expectedTooltipMsg + " non è visibile");
            throw e;
        } catch (Exception e) {
            Allure.addAttachment("Validazione Codice Fiscale Incolla", "Si è verificato un errore imprevisto durante il verifica della visibilità DBX: \n" + e.getMessage());
            throw e;
        }
        Allure.addAttachment("Expected Results", expectedResults);
    }

    @Test(dataProvider = "DatiCFValidi", dataProviderClass = DataProviders.class, priority = 4, testName = "Validazione Codice Fiscale Valido")
    @Severity(SeverityLevel.CRITICAL)
    public void validazioneCodiceFiscaleValido(String codiceFiscale, String optionType, String expectedTooltipMsg) throws InterruptedException {

        Map<String, String> testData = ExcelUtil.RetrieveTestData(EXCEL_FILE_PATH, 10);

        String testName = testData.get("TestName");
        String testDescription = testData.get("TestDescription");
        String expectedResults = testData.get("ExpectedResults");

        AllureUtils.updateAllureReport(testName, testDescription);

        anagrafeCensimentoProspect.clickCensimentoProspect();

        try {
            Assert.assertEquals(anagrafeCensimentoProspect.validazioneCodiceFiscaleEsistente(codiceFiscale, optionType), expectedTooltipMsg);

        } catch (AssertionError e) {
            Allure.addAttachment("Validazione Codice Fiscale Incolla", " Il tooltip " + expectedTooltipMsg + " non è visibile");
            throw e;
        } catch (Exception e) {
            Allure.addAttachment("Validazione Codice Fiscale Incolla", "Si è verificato un errore imprevisto durante il verifica della visibilità DBX: \n" + e.getMessage());
            throw e;
        }
        Allure.addAttachment("Expected Results", expectedResults);
    }

    @Test(dataProvider = "DatiCFValidi", dataProviderClass = DataProviders.class, priority = 5, testName = "Validazione Codice Fiscale Valido Incolla")
    @Severity(SeverityLevel.CRITICAL)
    public void validazioneCodiceFiscaleEsistenteIncolla(String codiceFiscale, String optionType,  String expectedTooltipMsg) throws InterruptedException {

        Map<String, String> testData = ExcelUtil.RetrieveTestData(EXCEL_FILE_PATH, 15);

        String testName = testData.get("TestName");
        String testDescription = testData.get("TestDescription");
        String expectedResults = testData.get("ExpectedResults");

        AllureUtils.updateAllureReport(testName, testDescription);

        anagrafeCensimentoProspect.clickCensimentoProspect();

        try {
            Assert.assertEquals(anagrafeCensimentoProspect.validazioneCodiceFiscaleEsistenteIncolla(codiceFiscale, optionType), expectedTooltipMsg);

        } catch (AssertionError e) {
            Allure.addAttachment("Validazione Codice Fiscale Valido Incolla", " Il tooltip " + expectedTooltipMsg + " non è visibile");
            throw e;
        } catch (Exception e) {
            Allure.addAttachment("Validazione Codice Fiscale Valido Incolla", "Si è verificato un errore imprevisto durante il verifica della visibilità DBX: \n" + e.getMessage());
            throw e; // Re-throw the exception to mark the test as failed
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

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

//@Epic("Ricerca per Partita IVA")
@Feature("Censimento Prospect PF Panel - Dati principali - Cognome")
@DisplayName("Censimento Prospect PF Panel - Dati principali - Cognome")
public class ValidazioneCognome extends BaseClass {

    public final String provider_name = "Validazione Cognome";
    private static String EXCEL_FILE_PATH ;
    private static AnagrafeCensimentoProspect anagrafeCensimentoProspect;

    private static final Map<String, AtomicInteger> testIdMap = new HashMap<>();

    static {
        // Initialize the map with test method names and their starting testIds
        testIdMap.put("validazioneCognome", new AtomicInteger(28));
        testIdMap.put("validazioneCognomeIncolla", new AtomicInteger(34));
        testIdMap.put("validazioneCognomeValido", new AtomicInteger(33));
        testIdMap.put("validazioneCognomeValidoIncolla", new AtomicInteger(39));
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

    @Test(priority = 1, testName = "Verifica Presenza Cognome")
    @Severity(SeverityLevel.CRITICAL)
    public void verificaPresenzaCognome() {
        anagrafeCensimentoProspect.clickCensimentoProspect();
        ExecuteUtils.executeTestTrueNoParam(EXCEL_FILE_PATH, 2, "Il campo Cognome", anagrafeCensimentoProspect.isLastNamePresent());
    }

    @Test(dataProvider = provider_name, dataProviderClass = DataProviders.class, priority = 2, testName = "Validazione Cognome")
    @Severity(SeverityLevel.CRITICAL)
    public void validazioneCognome(String cognome, String expectedTooltipMsg) throws InterruptedException {

        Map<String, String> testData = ExcelUtil.RetrieveTestData(EXCEL_FILE_PATH, getNextTestId("validazioneCognome"));
        String testName = testData.get("TestName");
        String testDescription = testData.get("TestDescription");
        String expectedResults = testData.get("ExpectedResults");

        AllureUtils.updateAllureReport(testName, testDescription);

        anagrafeCensimentoProspect.clickCensimentoProspect();

        try {
            Assert.assertEquals(anagrafeCensimentoProspect.validazioneCognome(cognome, expectedTooltipMsg), expectedTooltipMsg);

        } catch (AssertionError e){
            Allure.addAttachment("Validazione Cognome", " Il tooltip " + expectedTooltipMsg + " non è visibile");
            throw e;
        } catch (Exception e) {
            // Handle other unexpected exceptions
            Allure.addAttachment("Validazione Cognome", "Si è verificato un errore imprevisto durante il verifica della visibilità DBX: \n" + e.getMessage());
            throw e;
        }
    }

    @Test(dataProvider = provider_name, dataProviderClass = DataProviders.class, priority = 3, testName = "Validazione Cognome Incolla")
    @Severity(SeverityLevel.CRITICAL)
    public void validazioneCognomeIncolla(String cognome, String expectedTooltipMsg) throws InterruptedException {

        Map<String, String> testData = ExcelUtil.RetrieveTestData(EXCEL_FILE_PATH, getNextTestId("validazioneCognomeIncolla"));
        String testName = testData.get("TestName");
        String testDescription = testData.get("TestDescription");
        String expectedResults = testData.get("ExpectedResults");

        AllureUtils.updateAllureReport(testName, testDescription);

        anagrafeCensimentoProspect.clickCensimentoProspect();

        try {
            page.evaluate("cognome => navigator.clipboard.writeText(cognome)", cognome);
            Assert.assertEquals(anagrafeCensimentoProspect.validazioneCognomeIncolla(cognome), "Campo invalido");

        } catch (AssertionError e){
            Allure.addAttachment("Validazione Cognome Incolla", " Il tooltip " + expectedTooltipMsg + " non è visibile");
            throw e;
        } catch (Exception e) {
            Allure.addAttachment("Validazione Cognome Incolla", "Si è verificato un errore imprevisto durante il verifica della visibilità DBX: \n" + e.getMessage());
            throw e;
        }
    }

    @Test(dataProvider = "Dati PF", dataProviderClass = DataProviders.class, priority = 4, testName = "Validazione Cognome Valido")
    @Severity(SeverityLevel.CRITICAL)
    public void validazioneCognomeValido(String codiceFiscale, String cognome, String nome, String telefono, String email) throws InterruptedException {

        Map<String, String> testData = ExcelUtil.RetrieveTestData(EXCEL_FILE_PATH, getNextTestId("validazioneCognomeValido"));
        String testName = testData.get("TestName");
        String testDescription = testData.get("TestDescription");
        String expectedResults = testData.get("ExpectedResults");

        AllureUtils.updateAllureReport(testName, testDescription);

        anagrafeCensimentoProspect.clickCensimentoProspect();

        try {
            Assert.assertTrue(anagrafeCensimentoProspect.validazioneCognomeValido(cognome));

        } catch (AssertionError e){
            Allure.addAttachment("Validazione Cognome Valido", " Il tooltip è visibile");
            throw e;
        } catch (Exception e) {
            Allure.addAttachment("Validazione Cognome Valido", "Si è verificato un errore imprevisto durante il verifica della visibilità DBX: \n" + e.getMessage());
            throw e;
        }
    }

    @Test(dataProvider = "Dati PF", dataProviderClass = DataProviders.class, priority = 5, testName = "Validazione Cognome Valido Incolla")
    @Severity(SeverityLevel.CRITICAL)
    public void validazioneCognomeValidoIncolla(String codiceFiscale, String cognome, String nome, String telefono, String email) throws InterruptedException {

        Map<String, String> testData = ExcelUtil.RetrieveTestData(EXCEL_FILE_PATH, getNextTestId("validazioneCognomeValidoIncolla"));
        String testName = testData.get("TestName");
        String testDescription = testData.get("TestDescription");
        String expectedResults = testData.get("ExpectedResults");

        AllureUtils.updateAllureReport(testName, testDescription);

        anagrafeCensimentoProspect.clickCensimentoProspect();

        try {
            page.evaluate("cognome => navigator.clipboard.writeText(cognome)", cognome);
            Assert.assertTrue(anagrafeCensimentoProspect.validazioneCognomeValidoIncolla(cognome));

        } catch (AssertionError e){
            Allure.addAttachment("Validazione Cognome Valido Incolla", " Il tooltip è visibile");
            throw e;
        } catch (Exception e) {

            Allure.addAttachment("Validazione Cognome Valido Incolla", "Si è verificato un errore imprevisto durante il verifica della visibilità DBX: \n" + e.getMessage());
            throw e;

        }
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

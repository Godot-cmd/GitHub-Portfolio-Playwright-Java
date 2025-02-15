package tests.DBX634;

import base.BaseClass;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.testng.annotations.*;
import pom.anagrafe.AnagrafeCercaResultsPage;
import pom.anagrafe.AnagrafeMainPage;
import utils.ConfigProperties;
import utils.ExecuteUtils;
import utils.ExcelUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Feature("Ricerca puntuale base, interrogazione su singolo campo specifico - Codice Fiscale")
@DisplayName("Ricerca puntuale base, interrogazione su singolo campo specifico - Codice Fiscale")
public class CercaCodiceFiscale extends BaseClass {

    private static String EXCEL_FILE_PATH;
    private static AnagrafeMainPage anagrafePom;
    private static AnagrafeCercaResultsPage anagrafeCercaResultsPage;

    private static final Map<String, AtomicInteger> testIdMap = new HashMap<>();

    static {
        // Initialize the map with test method names and their starting testIds
        testIdMap.put("cercaPartitaIVA", new AtomicInteger(1));
        testIdMap.put("cercaPartitaIVAAutoComplete", new AtomicInteger(48));
        testIdMap.put("cercaCodiceFiscale", new AtomicInteger(3));
        testIdMap.put("cercaCodicefiscaleAutoComplete", new AtomicInteger(50));
    }

    private int getNextTestId(String testMethodName) {
        AtomicInteger testIdCounter = testIdMap.get(testMethodName);
        if (testIdCounter != null) {
            return testIdCounter.getAndIncrement();
        } else {
            throw new IllegalArgumentException("Test method not found in map: " + testMethodName);
        }
    }

    @DataProvider(name = "codiceFiscaleData")
    public Object[][] codiceFiscaleData() throws Exception {
        return ExcelUtil.getTableArray("CodiceFiscale");
    }

    @DataProvider(name = "partitaIVA")
    public Object[][] partitaIVA() throws Exception {
        return ExcelUtil.getTableArray("PartitaIVA");
    }

    @BeforeClass
    public static void initialize() throws InterruptedException {
        anagrafePom = new AnagrafeMainPage(page);
        anagrafeCercaResultsPage = new AnagrafeCercaResultsPage(page);
        ConfigProperties.initializePropertyFile();
        EXCEL_FILE_PATH = ConfigProperties.property.getProperty("DBX634");
    }

    @Test(dataProvider = "partitaIVA", priority = 1, testName = "Ricerca per PartitaIVA (Cerca)")
    @Severity(SeverityLevel.CRITICAL)
    public void cercaPartitaIVA(String cercaValue, String expectedCodiceFiscale, String type) throws InterruptedException {
        int testId = getNextTestId("cercaPartitaIVA");  //1
        ExecuteUtils.executeTestRicerca(EXCEL_FILE_PATH, testId, expectedCodiceFiscale + " non è visibile nei risultati " + type,
                () -> {
                    try {
                        anagrafePom.cerca(cercaValue);
                        anagrafePom.clickCercaBtn();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println(anagrafeCercaResultsPage.isCodiceFiscaleOrNdgVisible(expectedCodiceFiscale));
                    return anagrafeCercaResultsPage.isCodiceFiscaleOrNdgVisible(expectedCodiceFiscale);
                },
                () -> anagrafeCercaResultsPage.clickIndietro()
        );
    }

    @Test(dataProvider = "partitaIVA", priority = 2, testName = "Ricerca per Pertita IVA (Autocomplete)")
    @Severity(SeverityLevel.CRITICAL)
    public void cercaPartitaIVAAutoComplete(String cercaValue, String expectedCodiceFiscale, String type) throws InterruptedException {
        int testId = getNextTestId("cercaPartitaIVAAutoComplete");  //48
        ExecuteUtils.executeTestRicerca(EXCEL_FILE_PATH, testId, expectedCodiceFiscale + " non è visibile nel autocomplete " + type,
                () -> {
                    try {
                        anagrafePom.cerca(cercaValue);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return anagrafePom.isDropDownCodFiscVisible(expectedCodiceFiscale);
                }
        );
    }

    @Test(dataProvider = "codiceFiscaleData", priority = 3, testName = "Ricerca per Codice Fiscale (Cerca)")
    @Severity(SeverityLevel.CRITICAL)
    public void cercaCodiceFiscale(String cercaValue, String expectedCodiceFiscale, String type) throws InterruptedException {
        int testId = getNextTestId("cercaCodiceFiscale");   //3
        ExecuteUtils.executeTestRicerca(EXCEL_FILE_PATH, testId, expectedCodiceFiscale + " non è visibile nei risultati " + type,
                () -> {
                    try {
                        anagrafePom.cerca(cercaValue);
                        anagrafePom.clickCercaBtn();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return anagrafeCercaResultsPage.isCodiceFiscaleOrNdgVisible(expectedCodiceFiscale);
                },
                () -> anagrafeCercaResultsPage.clickIndietro()
        );
    }

    @Test(dataProvider = "codiceFiscaleData", priority = 4, testName = "Ricerca per Codice Fiscale (Autocomplete)")
    @Severity(SeverityLevel.CRITICAL)
    public void cercaCodicefiscaleAutoComplete(String cercaValue, String expectedCodiceFiscale, String type) throws InterruptedException {
        int testId = getNextTestId("cercaCodicefiscaleAutoComplete");   //50
        ExecuteUtils.executeTestRicerca(EXCEL_FILE_PATH, testId, expectedCodiceFiscale + " non è visibile nel autocomplete " + type,
                () -> {
                    try {
                        anagrafePom.cerca(cercaValue);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return anagrafePom.isDropDownCodFiscVisible(expectedCodiceFiscale);
                }
        );
    }

    @AfterClass
    public void cleanVariables() {
        //anagrafePom.refreshPage();
        /*anagrafePom = null;
        anagrafeCercaResultsPage = null;*/
    }
}


/*
package tests;

import base.BaseClass;
import io.qameta.allure.*;
        import org.junit.jupiter.api.DisplayName;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pom.anagrafe.AnagrafeCercaResultsPage;
import pom.anagrafe.AnagrafeMainPage;
import utils.AllureUtils;
import utils.ExcelUtil;

//@Epic("Ricerca per Partita IVA")
@Feature("Ricerca Partita IVA")
@DisplayName("Ricerca per Codice Fiscale")
public class CercaCodiceFiscale extends BaseClass {

    public final String provider_name = "codiceFiscaleData";

    @DataProvider(name = provider_name)
    public Object[][] codiceFiscaleData() throws Exception {

        Object[][] test = ExcelUtil.getTableArray("CodiceFiscale");
        return test;

    }

    private static AnagrafeMainPage anagrafePom;
    private static AnagrafeCercaResultsPage anagrafeCercaResultsPage;

    @BeforeClass
    public static void initialize() throws InterruptedException {
        anagrafePom = new AnagrafeMainPage(page);
        anagrafeCercaResultsPage = new AnagrafeCercaResultsPage(page);
    }

    @Test(dataProvider = provider_name, priority = 1, testName = "Ricerca per Codice Fiscale (Autocomplete)")
    @Severity(SeverityLevel.CRITICAL)
    public void cercaCodicefiscaleAutoComplete(String cercaValue, String expectedCodiceFiscale, String type) throws InterruptedException {

        AllureUtils.updateAllureReport("Ricerca per Codice Fiscale " + type + " (Autocomplete)", "Inserire la Codice Fiscale " + type + " nel campo di ricerca e visualizzare l'entità presente nei risultati di autocomplete");

        anagrafePom.cerca(cercaValue);

        try {
            */
/*Assert.assertTrue(anagrafePom.isDropDownCodFiscVisible("("+expectedCodiceFiscale+")"),
                    expectedCodiceFiscale+ " Il codice fiscale non è visibile nel autocomplete "+ type);*//*

            Assert.assertTrue(anagrafePom.isDropDownCodFiscVisible(expectedCodiceFiscale),
                    expectedCodiceFiscale+ " Il codice fiscale non è visibile nel autocomplete "+ type);
        } catch (AssertionError e){
            Allure.addAttachment("Ricerca per Codice Fiscale - (Autocomplete)", "Il codice fiscale non è visibile nel autocomplete.");
            throw e;
        } catch (Exception e) {
            // Handle other unexpected exceptions
            Allure.addAttachment("Ricerca per Codice Fiscale - (Autocomplete)", "Si è verificato un errore imprevisto durante il verifica della visibilità DBX: \n" + e.getMessage());
            throw e; // Re-throw the exception to mark the test as failed
        }
    }

    @Test(dataProvider = provider_name, priority = 2, testName = "Ricerca per Codice Fiscale (Cerca)")
    @Severity(SeverityLevel.CRITICAL)
    public void cercaCodiceFiscale(String cercaValue, String expectedCodiceFiscale, String type) throws InterruptedException {

        AllureUtils.updateAllureReport("Ricerca per Codice Fiscale " + type + " (Cerca)", "Inserire la Codice Fiscale " + type + " nel campo di ricerca e premere il pulsante Cerca");

        anagrafePom.cerca(cercaValue);
        anagrafePom.clickCercaBtn();

        try {
            Assert.assertTrue(anagrafeCercaResultsPage.isCodiceFiscaleOrNdgVisible(expectedCodiceFiscale),
                    expectedCodiceFiscale + " Il codice fiscale dai risultati non è visibile " + type);

        } catch (AssertionError e){
            Allure.addAttachment("Ricerca per Codice Fiscale - (Cerca)", "Il codice fiscale dai risultati non è visibile.");
            throw e;
        } catch (Exception e) {
            // Handle other unexpected exceptions
            Allure.addAttachment("Ricerca per Codice Fiscale - (Cerca)", "Si è verificato un errore imprevisto durante il verifica della visibilità DBX: \n" + e.getMessage());
            throw e; // Re-throw the exception to mark the test as failed
        } finally {
            anagrafeCercaResultsPage.clickIndietro();
        }
    }

    @AfterClass
    public void cleanVariables() {
        try {
            anagrafePom = null;
            anagrafeCercaResultsPage = null;
        } catch (Exception e) {}
    }
}*/

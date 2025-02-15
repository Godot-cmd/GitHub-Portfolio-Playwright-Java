package tests.WorkflowCensimentoProspect;

import base.BaseClass;
import dataproviders.DataProviders;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pom.censimento.AnagrafeCensimentoProspect;
import utils.AllureUtils;
import utils.ConfigProperties;
import utils.ExcelUtil;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Feature("Having a workflow for the onboarding of Persona Fisica Prospect")
@DisplayName("Having a workflow for the onboarding of Persona Fisica Prospect")
public class VerificaWorkflow extends BaseClass {

    private static String EXCEL_FILE_PATH ;
    private static AnagrafeCensimentoProspect anagrafeCensimentoProspect;
    private static final Map<String, AtomicInteger> testIdMap = new HashMap<>();

    static {
        // Initialize the map with test method names and their starting testIds
        testIdMap.put("validazioneCodiceFiscale", new AtomicInteger(6));
        testIdMap.put("validazioneCodiceIncola", new AtomicInteger(11));
    }


    @BeforeClass
    public static void initialize() throws InterruptedException {
        anagrafeCensimentoProspect = new AnagrafeCensimentoProspect(page);
        ConfigProperties.initializePropertyFile();
        EXCEL_FILE_PATH = ConfigProperties.property.getProperty("DBX608");
    }

    @Test(priority = 1, testName = "Verifica Workflow - Persona Fisica")
    @Severity(SeverityLevel.CRITICAL)
    public void verificaWorkflowFisica() throws InterruptedException {
        Map<String, String> testData = ExcelUtil.RetrieveTestData(EXCEL_FILE_PATH, 1);
        String testName = testData.get("TestName");
        String testDescription = testData.get("TestDescription");
        String expectedResults = testData.get("ExpectedResults");

        AllureUtils.updateAllureReport(testName, testDescription);

        anagrafeCensimentoProspect.clickCensimentoProspect();

        try {
            Assert.assertTrue(anagrafeCensimentoProspect.verificaPresenzaPFPG("Persona Fisica"));
        } catch (AssertionError e) {
            Allure.addAttachment(testName, "Il workflow Persona Fisica non è visibile");
            throw e;
        } catch (Exception e) {
            Allure.addAttachment(testName, "Si è verificato un errore imprevisto durante la verifica della visibilità DBX: \n" + e.getMessage());
            throw e;
        }
        Allure.addAttachment("Expected Results", expectedResults);
    }

    @Test(priority = 2, testName = "Verifica Workflow - Persona Giuridica")
    @Severity(SeverityLevel.CRITICAL)
    public void verificaWorkflowGiuridica() throws InterruptedException {
        Map<String, String> testData = ExcelUtil.RetrieveTestData(EXCEL_FILE_PATH, 2);
        String testName = testData.get("TestName");
        String testDescription = testData.get("TestDescription");
        String expectedResults = testData.get("ExpectedResults");

        AllureUtils.updateAllureReport(testName, testDescription);

        anagrafeCensimentoProspect.clickCensimentoProspect();

        try {
            Assert.assertTrue(anagrafeCensimentoProspect.verificaPresenzaPFPG("Persona Giuridica"));
        } catch (AssertionError e) {
            Allure.addAttachment(testName, "Il tooltip non è visibile");
            throw e;
        } catch (Exception e) {
            Allure.addAttachment(testName, "Si è verificato un errore imprevisto durante la verifica della visibilità DBX: \n" + e.getMessage());
            throw e;
        }
        Allure.addAttachment("Expected Results", expectedResults);
    }

    @Test(priority = 3, testName = "Workflow - Persona Giuridica - Verifica Tasto Annulla")
    @Severity(SeverityLevel.CRITICAL)
    public void verificaTastoAnnullaPG() throws InterruptedException {
        Map<String, String> testData = ExcelUtil.RetrieveTestData(EXCEL_FILE_PATH, 7);
        String testName = testData.get("TestName");
        String testDescription = testData.get("TestDescription");
        String expectedResults = testData.get("ExpectedResults");

        AllureUtils.updateAllureReport(testName, testDescription);

        anagrafeCensimentoProspect.clickCensimentoProspect();

        try {
            Assert.assertTrue(anagrafeCensimentoProspect.verificatastoAnnulla());
        } catch (AssertionError e) {
            Allure.addAttachment(testName, "Il tooltip non è visibile");
            throw e;
        } catch (Exception e) {
            Allure.addAttachment(testName, "Si è verificato un errore imprevisto durante la verifica della visibilità DBX: \n" + e.getMessage());
            throw e;

        }
        Allure.addAttachment("Expected Results", expectedResults);
    }

    @Test(priority = 4, testName = "Workflow - Persona Fisica - Verifica Tasto Annulla")
    @Severity(SeverityLevel.CRITICAL)
    public void verificaTastoAnnullaPF() throws InterruptedException {
        Map<String, String> testData = ExcelUtil.RetrieveTestData(EXCEL_FILE_PATH, 8);
        String testName = testData.get("TestName");
        String testDescription = testData.get("TestDescription");
        String expectedResults = testData.get("ExpectedResults");

        AllureUtils.updateAllureReport(testName, testDescription);

        anagrafeCensimentoProspect.clickCensimentoProspect();

        try {
            Assert.assertTrue(anagrafeCensimentoProspect.verificatastoAnnulla());
        } catch (AssertionError e) {
            Allure.addAttachment(testName, "Il tooltip non è visibile");
            throw e;
        } catch (Exception e) {
            Allure.addAttachment(testName, "Si è verificato un errore imprevisto durante la verifica della visibilità DBX: \n" + e.getMessage());
            throw e;
        }
        Allure.addAttachment("Expected Results", expectedResults);
    }

    @Test(priority = 5, testName = "Workflow - Persona Fisica - Verifica Tasto Annulla - Almeno un campo valorizzato")
    @Severity(SeverityLevel.CRITICAL)
    public void verificaTastoAnnullaPFAlmenoUnCampo() throws InterruptedException {
        Map<String, String> testData = ExcelUtil.RetrieveTestData(EXCEL_FILE_PATH, 11);
        String testName = testData.get("TestName");
        String testDescription = testData.get("TestDescription");
        String expectedResults = testData.get("ExpectedResults");

        AllureUtils.updateAllureReport(testName, testDescription);

        anagrafeCensimentoProspect.clickCensimentoProspect();
        anagrafeCensimentoProspect.inserimentoPF("A", "A", "", "", "");

        try {
            Assert.assertTrue(anagrafeCensimentoProspect.verificatastoAnnulla());
        } catch (AssertionError e) {
            Allure.addAttachment(testName, "Il tooltip non è visibile");
            throw e;
        } catch (Exception e) {
            Allure.addAttachment(testName, "Si è verificato un errore imprevisto durante la verifica della visibilità DBX: \n" + e.getMessage());
            throw e;

        }
        Allure.addAttachment("Expected Results", expectedResults);
    }

    @Test(priority = 6, testName = "Workflow - Persona Fisica - Verifica Tasto Exit - Almeno un campo valorizzato")
    @Severity(SeverityLevel.CRITICAL)
    public void verificaTastoExitPFAlmenoUnCampo() throws InterruptedException {
        Map<String, String> testData = ExcelUtil.RetrieveTestData(EXCEL_FILE_PATH, 12);
        String testName = testData.get("TestName");
        String testDescription = testData.get("TestDescription");
        String expectedResults = testData.get("ExpectedResults");

        AllureUtils.updateAllureReport(testName, testDescription);

        anagrafeCensimentoProspect.clickCensimentoProspect();
        anagrafeCensimentoProspect.inserimentoPF("A", "A", "", "", "");

        try {
            anagrafeCensimentoProspect.verificatastoExit();
        } catch (AssertionError e) {
            Allure.addAttachment(testName, "Il tooltip non è visibile");
            throw e;
        } catch (Exception e) {
            Allure.addAttachment(testName, "Si è verificato un errore imprevisto durante la verifica della visibilità DBX: \n" + e.getMessage());
            throw e;
        }
        Allure.addAttachment("Expected Results", expectedResults);
    }

    @Test(priority = 7, testName = "Workflow - Persona Fisica - Verifica Tasto Exit - Nessun campo valorizzato")
    @Severity(SeverityLevel.CRITICAL)
    public void verificaTastoExitPFNessunCampo() throws InterruptedException {
        Map<String, String> testData = ExcelUtil.RetrieveTestData(EXCEL_FILE_PATH, 13);
        String testName = testData.get("TestName");
        String testDescription = testData.get("TestDescription");
        String expectedResults = testData.get("ExpectedResults");

        AllureUtils.updateAllureReport(testName, testDescription);

        anagrafeCensimentoProspect.clickCensimentoProspect();

        try {
            anagrafeCensimentoProspect.verificatastoExit();
        } catch (AssertionError e) {
            Allure.addAttachment(testName, "Il tooltip non è visibile");
            throw e;
        } catch (Exception e) {
            Allure.addAttachment(testName, "Si è verificato un errore imprevisto durante la verifica della visibilità DBX: \n" + e.getMessage());
            throw e;
        }
        Allure.addAttachment("Expected Results", expectedResults);
    }

    @Test(priority = 8, testName = "Workflow - Persona Fisica - Verifica Tasto Prosegui")
    @Severity(SeverityLevel.CRITICAL)
    public void verificaTastoProseguiPF() throws InterruptedException {
        Map<String, String> testData = ExcelUtil.RetrieveTestData(EXCEL_FILE_PATH, 20);
        String testName = testData.get("TestName");
        String testDescription = testData.get("TestDescription");
        String expectedResults = testData.get("ExpectedResults");

        AllureUtils.updateAllureReport(testName, testDescription);

        anagrafeCensimentoProspect.clickCensimentoProspect();

        try {
            Assert.assertTrue(anagrafeCensimentoProspect.verificatastoProsegui());
        } catch (AssertionError e) {
            Allure.addAttachment(testName, "Il tooltip non è visibile");
            throw e;
        } catch (Exception e) {
            Allure.addAttachment(testName, "Si è verificato un errore imprevisto durante la verifica della visibilità DBX: \n" + e.getMessage());
            throw e;
        }
        Allure.addAttachment("Expected Results", expectedResults);
    }

    @Test(priority = 8, testName = "Workflow - Persona Fisica - Verifica Tasto Prosegui - Un Campo Valorizzato")
    @Severity(SeverityLevel.CRITICAL)
    public void verificaTastoProseguiPFunCampo() throws InterruptedException {
        Map<String, String> testData = ExcelUtil.RetrieveTestData(EXCEL_FILE_PATH, 26);
        String testName = testData.get("TestName");
        String testDescription = testData.get("TestDescription");
        String expectedResults = testData.get("ExpectedResults");

        AllureUtils.updateAllureReport(testName, testDescription);

        anagrafeCensimentoProspect.clickCensimentoProspect();
        anagrafeCensimentoProspect.inserimentoPF("A", "", "", "", "");
        anagrafeCensimentoProspect.clickProsegui();

        try {
            Assert.assertTrue(anagrafeCensimentoProspect.verificaWarningCF());
        } catch (AssertionError e) {
            Allure.addAttachment(testName, "Il tooltip non è visibile");
            throw e;
        } catch (Exception e) {
            Allure.addAttachment(testName, "Si è verificato un errore imprevisto durante la verifica della visibilità DBX: \n" + e.getMessage());
            throw e;
        }
        Allure.addAttachment("Expected Results", expectedResults);
    }

    @Test(priority = 9, testName = "Workflow - Persona Fisica - Verifica Tasto Prosegui - Due Campi Valorizzati")
    @Severity(SeverityLevel.CRITICAL)
    public void verificaTastoProseguiPFdueCampi() throws InterruptedException {
        Map<String, String> testData = ExcelUtil.RetrieveTestData(EXCEL_FILE_PATH, 27);
        String testName = testData.get("TestName");
        String testDescription = testData.get("TestDescription");

        AllureUtils.updateAllureReport(testName, testDescription);

        anagrafeCensimentoProspect.clickCensimentoProspect();
        anagrafeCensimentoProspect.inserimentoPF("A", "A", "", "", "");
        anagrafeCensimentoProspect.clickProsegui();

        try {
            Assert.assertTrue(anagrafeCensimentoProspect.verificaWarningCF() && anagrafeCensimentoProspect.verificaWarningCognome());
        } catch (AssertionError e) {
            Allure.addAttachment(testName, "Il tooltip non è visibile");
            throw e;
        } catch (Exception e) {
            Allure.addAttachment(testName, "Si è verificato un errore imprevisto durante la verifica della visibilità DBX: \n" + e.getMessage());
            throw e;
        }
    }

    @AfterMethod
    public void tearDown(ITestResult result) {

        String methodName = result.getMethod().getMethodName();

        // Skip tearDown for specific test methods
        if ("verificaTastoExitPFAlmenoUnCampo".equals(methodName) || "verificaTastoExitPFNessunCampo".equals(methodName)) {
            return;
        }

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
